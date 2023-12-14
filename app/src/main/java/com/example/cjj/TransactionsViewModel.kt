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

/**
 * Represents a model for transaction lists and how they are displayed.
 * @param transactions The list of transactions.
 * @param sortOrder The order by which the transaction list is sorted.
 */
data class TransactionsUiModel(
    val transactions: List<Transaction>,
    val sortOrder: SortOrder
)

/**
 * The ViewModel for all the transactions.
 * @param repository The repository for the transactions.
 * @param userPreferencesRepository The repository for user preferences.
 */
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

    /**
     * Toggles whether the transaction list should be sorted by date.
     * @param enable The "yes" or "no".
     * @param accountType The type of account.
     */
    fun enableSortByDate(enable: Boolean, accountType: AccountType) {
        viewModelScope.launch {
            userPreferencesRepository.enableSortByDate(enable, accountType)
        }
    }

    /**
     * Toggles whether the transaction list should be sorted alphabetically.
     * @param enable The "yes" or "no".
     * @param accountType The type of account.
     */
    fun enableSortAlphabetically(enable: Boolean, accountType: AccountType) {
        viewModelScope.launch {
            userPreferencesRepository.enableSortAlphabetically(enable, accountType)
        }
    }
}

/**
 * The factory that produces TransactionsViewModels
 * @param repository The repository for transactions.
 * @param userPreferencesRepository The repository for user preferences.
 */
class TransactionsViewModelFactory(
    private val repository: TransactionsRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of TransactionsViewModel, set with an TransactionsRepository and UserPreferencesRepository.
     * @param modelClass The class that will take in the TransactionsViewModel.
     * @return A new instance of TransactionsViewModel.
     * @throws IllegalArgumentException When the modelClass cannot take in the TransactionsViewModel.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransactionsViewModel(repository, userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
