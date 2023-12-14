package com.example.cjj.data

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * The type of account in the bank.
 */
enum class AccountType
{
    NONE,
    CHEQUING,
    SAVINGS,
    CREDIT
}

/**
 * An account in the bank that stores balance, transactions, and possibly debt.
 * @param type The type of account.
 * @param balance The amount of money stored for this account.
 * @param transactions The list of transactions for this account.
 * @param dueDate The due date of a debt that needs to be repaid.
 */
data class Account(
    val type: AccountType,
    var balance: Double = 0.0,
    var transactions: MutableStateFlow<List<Transaction>>,
    var dueDate: String? = null
) {
    /**
     * Adds a given transaction to the account's list of transactions.
     * @param transaction The transaction to add.
     */
    fun addTransaction(transaction: Transaction) {

        balance+=transaction.amount
        transaction.subtotal = balance
        var list = transactions.value.toMutableList()
        list.add(transaction)
        transactions.value = list

        Log.d("TAG", "test: ${transactions.value.get(transactions.value.size - 1).amount}")
    }
}

/**
 * A transaction in an account.
 * @param date The date that the transaction occurred.
 * @param amount The amount of money traded in the transaction.
 * @param detail The transaction's description.
 * @param subtotal The amount of money traded before tax.
 */
data class Transaction(
    val date: String,
    val amount: Double,
    val detail: String,
    var subtotal: Double? = null
)