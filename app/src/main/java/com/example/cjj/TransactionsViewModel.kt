package com.example.cjj

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.cjj.data.AccountType
import com.example.cjj.data.SortOrder
import com.example.cjj.data.Transaction
import com.example.cjj.data.TransactionsRepository
import com.example.cjj.data.UserPreferences
import com.example.cjj.data.UserPreferencesRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class TransactionsUiModel(
    val transactions: List<Transaction>,
    val sortOrder: SortOrder
)

class TransactionsViewModel(
    repository: TransactionsRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow

    /**
     * Chequing
     */
    private val chequingTransactionsUiModelFlow = combine(
        repository.chequingTransactions,
        userPreferencesFlow
    ) { transactions: List<Transaction>, userPreferences: UserPreferences ->
        return@combine TransactionsUiModel(
            transactions = filterSortTransactions(
                transactions,
                userPreferences.chequingSortOrder
            ),
            sortOrder = userPreferences.chequingSortOrder
        )
    }
    private val chequingTransactionsUiModel = chequingTransactionsUiModelFlow.asLiveData()
    var chequingTransactions by mutableStateOf(chequingTransactionsUiModel.value?.transactions)

    /**
     * Date of retrieval: 2023/12/11
     * I needed a way to update a LazyColumn with LiveData values,
     * because changes to LiveData don't automatically trigger recompositions.
     * https://stackoverflow.com/questions/71289865/lazycolumn-item-not-updated-accordingly-while-list-in-room-table-already-updated
     */
    init {
        chequingTransactionsUiModel.observeForever {
            chequingTransactions = it.transactions
        }
    }

    /**
     * Savings
     */
    private val savingsTransactionsUiModelFlow = combine(
        repository.savingsTransactions,
        userPreferencesFlow
    ) { transactions: List<Transaction>, userPreferences: UserPreferences ->
        return@combine TransactionsUiModel(
            transactions = filterSortTransactions(
                transactions,
                userPreferences.savingsSortOrder
            ),
            sortOrder = userPreferences.savingsSortOrder
        )
    }
    private val savingsTransactionsUiModel = savingsTransactionsUiModelFlow.asLiveData()
    var savingsTransactions by mutableStateOf(savingsTransactionsUiModel.value?.transactions)
    init {
        savingsTransactionsUiModel.observeForever {
            savingsTransactions = it.transactions
        }
    }

    /**
     * Credit
     */
    private val creditTransactionsUiModelFlow = combine(
        repository.creditTransactions,
        userPreferencesFlow
    ) { transactions: List<Transaction>, userPreferences: UserPreferences ->
        return@combine TransactionsUiModel(
            transactions = filterSortTransactions(
                transactions,
                userPreferences.creditSortOrder
            ),
            sortOrder = userPreferences.creditSortOrder
        )
    }
    private val creditTransactionsUiModel = creditTransactionsUiModelFlow.asLiveData()
    var creditTransactions by mutableStateOf(creditTransactionsUiModel.value?.transactions)
    init {
        creditTransactionsUiModel.observeForever {
            creditTransactions = it.transactions
        }
    }

    private fun filterSortTransactions(
        transactions: List<Transaction>,
        sortOrder: SortOrder
    ): List<Transaction> {

        // sort the transactions
        return when (sortOrder) {
            SortOrder.NONE -> transactions
            SortOrder.DATE -> transactions.sortedByDescending { it.date }.toMutableList()
            SortOrder.ALPHABETICALLY -> transactions.sortedBy { it.detail }.toMutableList()
            SortOrder.DATE_AND_ALPHABETICALLY -> transactions.sortedWith(
                compareByDescending<Transaction> { it.date }.thenBy { it.detail }
            ).toMutableList()
        }
    }

    fun enableSortByDate(enable: Boolean, accountType: AccountType) {
        viewModelScope.launch {
            userPreferencesRepository.enableSortByDate(enable, accountType)
        }
    }

    fun enableSortAlphabetically(enable: Boolean, accountType: AccountType) {
        viewModelScope.launch {
            userPreferencesRepository.enableSortAlphabetically(enable, accountType)
        }
    }
}

class TransactionsViewModelFactory(
    private val repository: TransactionsRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransactionsViewModel(repository, userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
