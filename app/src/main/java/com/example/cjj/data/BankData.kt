package com.example.cjj.data

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow

enum class AccountType
{
    NONE,
    CHEQUING,
    SAVINGS,
    CREDIT
}

data class Account(
    val type: AccountType,
    var balance: Double = 0.0,
    var transactions: MutableStateFlow<List<Transaction>>,
    var dueDate: String? = null
) {
    fun addTransaction(transaction: Transaction) {

        balance+=transaction.amount
        transaction.subtotal = balance
        var list = transactions.value.toMutableList()
        list.add(transaction)
        transactions.value = list

        Log.d("TAG", "test: ${transactions.value.get(transactions.value.size - 1).amount}")
    }
}

data class Transaction(
    val date: String,
    val amount: Double,
    val detail: String,
    var subtotal: Double? = null
)