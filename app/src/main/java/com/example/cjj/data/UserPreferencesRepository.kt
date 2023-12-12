/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.cjj.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val USER_PREFERENCES_NAME = "user_preferences"
private const val SORT_ORDER_KEY = "sort_order"

enum class SortOrder {
    NONE,
    DATE,
    ALPHABETICALLY,
    DATE_AND_ALPHABETICALLY
}

data class UserPreferences(
    val chequingSortOrder: SortOrder,
    val savingsSortOrder: SortOrder,
    val creditSortOrder: SortOrder
)

/**
 * Class that handles saving and retrieving user preferences
 */
class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>,
    context: Context
) {
    private object PreferencesKeys {
        val CHEQUING_SORT_ORDER = stringPreferencesKey("chequing_sort_order")
        val SAVINGS_SORT_ORDER = stringPreferencesKey("savings_sort_order")
        val CREDIT_SORT_ORDER = stringPreferencesKey("credit_sort_order")
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val chequingSortOrder =
                SortOrder.valueOf(
                    preferences[PreferencesKeys.CHEQUING_SORT_ORDER] ?: SortOrder.NONE.name)
            val savingsSortOrder =
                SortOrder.valueOf(
                    preferences[PreferencesKeys.SAVINGS_SORT_ORDER] ?: SortOrder.NONE.name
                )
            val creditSortOrder =
                SortOrder.valueOf(
                    preferences[PreferencesKeys.CREDIT_SORT_ORDER] ?: SortOrder.NONE.name
                )

            UserPreferences(chequingSortOrder, savingsSortOrder, creditSortOrder)
        }

    suspend fun enableSortByDate(enable: Boolean, accountType: AccountType) {
        var preferencesKeysType = PreferencesKeys.CHEQUING_SORT_ORDER
        if (accountType == AccountType.SAVINGS) {
            preferencesKeysType = PreferencesKeys.SAVINGS_SORT_ORDER
        } else if (accountType == AccountType.CREDIT) {
            preferencesKeysType = PreferencesKeys.CREDIT_SORT_ORDER
        }

        dataStore.edit { preferences ->
            val currentOrder = SortOrder.valueOf(
                preferences[preferencesKeysType] ?: SortOrder.NONE.name
            )

            val newSortOrder =
                if (enable) {
                    if (currentOrder == SortOrder.ALPHABETICALLY) {
                        SortOrder.DATE_AND_ALPHABETICALLY
                    } else {
                        SortOrder.DATE
                    }
                } else {
                    if (currentOrder == SortOrder.DATE_AND_ALPHABETICALLY) {
                        SortOrder.ALPHABETICALLY
                    } else {
                        SortOrder.NONE
                    }
                }
            preferences[preferencesKeysType] = newSortOrder.name
        }
    }

    private val sharedPreferences =
        context.applicationContext.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)

    // Keep the sort order as a stream of changes
    private val _sortOrderFlow = MutableStateFlow(sortOrder)
    val sortOrderFlow: StateFlow<SortOrder> = _sortOrderFlow

    /**
     * Get the sort order. By default, sort order is None.
     */
    private val sortOrder: SortOrder
        get() {
            val order = sharedPreferences.getString(SORT_ORDER_KEY, SortOrder.NONE.name)
            return SortOrder.valueOf(order ?: SortOrder.NONE.name)
        }

    suspend fun enableSortAlphabetically(enable: Boolean, accountType: AccountType) {
        var preferencesKeysType = PreferencesKeys.CHEQUING_SORT_ORDER
        if (accountType == AccountType.SAVINGS) {
            preferencesKeysType = PreferencesKeys.SAVINGS_SORT_ORDER
        } else if (accountType == AccountType.CREDIT) {
            preferencesKeysType = PreferencesKeys.CREDIT_SORT_ORDER
        }

        // edit handles data transactionally, ensuring that if the sort is updated at the same
        // time from another thread, we won't have conflicts
        dataStore.edit { preferences ->
            // Get the current SortOrder as an enum
            val currentOrder = SortOrder.valueOf(
                preferences[preferencesKeysType] ?: SortOrder.NONE.name
            )

            val newSortOrder =
                if (enable) {
                    if (currentOrder == SortOrder.DATE) {
                        SortOrder.DATE_AND_ALPHABETICALLY
                    } else {
                        SortOrder.ALPHABETICALLY
                    }
                } else {
                    if (currentOrder == SortOrder.DATE_AND_ALPHABETICALLY) {
                        SortOrder.DATE
                    } else {
                        SortOrder.NONE
                    }
                }
            preferences[preferencesKeysType] = newSortOrder.name
        }
    }
}

