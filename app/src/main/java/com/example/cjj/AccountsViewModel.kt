package com.example.cjj

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cjj.data.SortOrder
import com.example.cjj.data.UserPreferences
import com.example.cjj.data.UserPreferencesRepository
import com.example.cjj.data.Account
import com.example.cjj.data.AccountsRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import com.example.cjj.data.AccountType
import com.example.cjj.data.Transaction
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch


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

    /*
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
*/

    private val _accountsUiModelFlow = MutableStateFlow(AccountsUiModel(emptyList(), false, SortOrder.NONE))


    val accountsUiModel = _accountsUiModelFlow.asStateFlow()


    init {


        val chequingAccount = Account(
            type = AccountType.CHEQUING,
        )
        fillChequingTransactions(chequingAccount)

        val savingsAccount = Account(
            type = AccountType.SAVINGS,
        )
        fillSavingsTransactions(savingsAccount)

        val creditAccount = Account(
            type = AccountType.CREDIT,
            dueDate = "2023-11-24",
        )
        fillCreditTransactions(creditAccount)



        combine(
            repository.accounts,
            userPreferencesFlow
        ) { accounts: List<Account>, userPreferences: UserPreferences ->
            _accountsUiModelFlow.value = AccountsUiModel(
                accounts = listOf(chequingAccount,savingsAccount,creditAccount),
                showCompleted = userPreferences.showCompleted,
                sortOrder = userPreferences.sortOrder
            )
        }.launchIn(viewModelScope)


    }

    fun getAccountByType(accountType: AccountType): Account? {
        return accountsUiModel.value?.accounts?.find { it.type == accountType }
    }

    /*
    fun fillAccounts() {

        viewModelScope.launch {
            val chequingAccount: Account? = getAccountByType(AccountType.CHEQUING)
            _accountsUiModelFlow.value.accounts.get(AccountType.CREDIT)
            if (chequingAccount != null) {
                fillChequingTransactions(chequingAccount)
            }

            val savingsAccount: Account? = getAccountByType(AccountType.SAVINGS)

            if (savingsAccount != null) {
                fillSavingsTransactions(savingsAccount)
            }

            val creditAccount: Account? = getAccountByType(AccountType.CREDIT)

            if (creditAccount != null) {
                fillCreditTransactions(creditAccount)
            }

            // Update the accountsUiModel after filling transactions
            _accountsUiModelFlow.value = AccountsUiModel(
                accounts = filterSortAccounts(
                    listOf(),  // use the repository accounts directly
                    userPreferencesFlow.showCompleted,
                    userPreferencesFlow.value.sortOrder
                ),
                showCompleted = userPreferencesFlow.value.showCompleted,
                sortOrder = userPreferencesFlow.value.sortOrder
            )
        }

    }

     */

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

fun fillChequingTransactions(account: Account)
{

    account.addTransaction(Transaction("2023-11-22", 100.00, "Transfer"))
    account.addTransaction(Transaction("2023-11-15", -13.00, "Boston Pizza"))
    account.addTransaction(Transaction("2023-11-08", -3.00, "Depanneur 123"))
    account.addTransaction(Transaction("2023-11-01", -14.00, "Jean Coutu"))
    account.addTransaction(Transaction("2023-10-25", 60.00, "Transfer"))
    account.addTransaction(Transaction("2023-10-18", -23.00, "McDonald's"))
    account.addTransaction(Transaction("2023-10-11", -7.00, "Couche-Tard"))
    account.addTransaction(Transaction("2023-10-04", 500.00, "Transfer"))
    account.addTransaction(Transaction("2023-09-27", -14.00, "Uniprix"))
    account.addTransaction(Transaction("2023-09-20", -46.00, "Gym"))
}

fun fillSavingsTransactions(account: Account)
{
    account.addTransaction(Transaction("2023-11-22", -100.00, "Transfer"))
    account.addTransaction(Transaction("2023-11-15", 20.00, "Interest"))
    account.addTransaction(Transaction("2023-11-08", 50.00, "Deposit"))
    account.addTransaction(Transaction("2023-11-01", 20.00, "Interest"))
    account.addTransaction(Transaction("2023-10-25", -60.00, "Transfer"))
    account.addTransaction(Transaction("2023-10-18", 20.00, "Interest"))
    account.addTransaction(Transaction("2023-10-11", -70.00, "Transfer"))
    account.addTransaction(Transaction("2023-10-04", 20.00, "Interest"))
    account.addTransaction(Transaction("2023-09-27", 400.00, "Deposit"))
    account.addTransaction(Transaction("2023-09-20", 20.00, "Interest"))
}


fun fillCreditTransactions(account: Account)
{
    account.addTransaction(Transaction("2023-10-18", 549.00, "Rent"))
    account.addTransaction(Transaction("2023-10-11", 50.00, "GazMetro"))
    account.addTransaction(Transaction("2023-09-27", 400.00, "Tuition"))

}