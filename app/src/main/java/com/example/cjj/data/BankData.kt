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
    var dueDate: String? = null
)
{
    var transactions: MutableList<Transaction> = mutableListOf()

    var balance: Double = 0.0

    fun addTransaction(transaction: Transaction) {

        balance+=transaction.amount
transaction.subtotal = balance
        transactions.add(transaction)
    }
}
data class Transaction(
    val date: String,
    val amount: Double,
    val detail: String
)
{
    var subtotal : Double? = null
}