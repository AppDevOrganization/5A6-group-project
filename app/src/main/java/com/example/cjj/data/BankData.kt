package com.example.cjj.data

enum class AccountType
{
    NONE,
    CHEQUING,
    SAVINGS,
    CREDIT
}

data class Account(
    val type : AccountType,
    var transactions: MutableList<Transaction>,
    var dueDate: String? = null
)
{
    val balance: Double
        get() = transactions.sumOf { it.amount }

    fun addTransaction(transaction: Transaction) {
        transactions.add(transaction)
    }
}
data class Transaction(
    val date: String,
    val amount: Double,
    val detail: String,
    val subtotal: Double
)