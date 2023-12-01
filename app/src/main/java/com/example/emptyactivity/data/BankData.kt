package com.example.emptyactivity.data

enum class AccountType
{
    NONE,
    CHEQUING,
    SAVINGS,
    CREDIT
}

data class Account(
    val type : AccountType,
    val balance: Double,
    var transactions: MutableList<Transaction>,
    var dueDate: String? = null
)
data class Transaction(
    val date: String,
    val amount: Double,
    val detail: String,
    val subtotal: Double
)