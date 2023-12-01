package com.example.emptyactivity.data

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow

//class UserPreferencesRepositoryDataStore(private val context: Context) {//: UserPreferencesRepository {
//    companion object {
////        val NAME = stringPreferencesKey("NAME")
////        val COUNTER = intPreferencesKey("COUNTER")
//    }
//
//    /** Update the values in the DataStore. */
//    suspend fun saveProfile(profileData: Account) {
////        context.dataStore.edit {
////            it[NAME] = profileData.name
////            it[COUNTER] = profileData.counter
////        }
//    }
//
//    /** Get the data in the DataStore as a flow.  Since the store may have never
//     *     been used yet, handle the null case with default values. */
//    fun getProfile(): Flow<Account> = context.dataStore.data.map {
////        ProfileData(
////            name = it[NAME] ?: "",
////            counter = it[COUNTER] ?: 0
////        )
//    }
//
//    suspend fun clear() {
//        context.dataStore.edit {
//            it.clear()
//        }
//    }
//}