package com.example.emptyactivity

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.emptyactivity.data.SortOrder
import com.example.emptyactivity.data.UserPreferences
import com.example.emptyactivity.data.UserPreferencesRepository
import com.example.emptyactivity.data.Account
import com.example.emptyactivity.data.AccountsRepository
import kotlinx.coroutines.flow.combine
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn


data class AccountsUiModel(
    val accounts: List<Account>,
    val showCompleted: Boolean,
    val sortOrder: SortOrder
)
class AccountsViewModel(
    repository: AccountsRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow

    private val accountsUiModelFlow = combine(
        repository.accounts,
        userPreferencesFlow
    ) { accounts: List<Account>, userPreferences: UserPreferences ->
        return@combine AccountsUiModel(
            accounts = filterSortAccounts(
                accounts,
                userPreferences.showCompleted,
                userPreferences.sortOrder
            ),
            showCompleted = userPreferences.showCompleted,
            sortOrder = userPreferences.sortOrder
        )
    }


    private val _accountsUiModelFlow = MutableStateFlow(AccountsUiModel(emptyList(), false, SortOrder.NONE))
    val accountsUiModel = _accountsUiModelFlow.asStateFlow()
    init {
        combine(
            repository.accounts,
            userPreferencesFlow
        ) { accounts: List<Account>, userPreferences: UserPreferences ->
            _accountsUiModelFlow.value = AccountsUiModel(
                accounts = filterSortAccounts(
                    accounts,
                    userPreferences.showCompleted,
                    userPreferences.sortOrder
                ),
                showCompleted = userPreferences.showCompleted,
                sortOrder = userPreferences.sortOrder
            )
        }.launchIn(viewModelScope)


    }

    fun getAccountByType(accountName: String): Account? {
        return accountsUiModel.value?.accounts?.find { it.name == accountName }
    }


    private fun filterSortAccounts(
        accounts: List<Account>,
        showCompleted: Boolean,
        sortOrder: SortOrder
    ): List<Account> {
        // filter the tasks
        val filteredAccounts = if (showCompleted) {
           accounts
        } else {
            accounts
        }
        // sort the tasks

        return when (sortOrder) {
            SortOrder.NONE -> filteredAccounts
        }
    }



}


class AccountsViewModelFactory(
    private val repository: AccountsRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AccountsViewModel(repository, userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}