package com.example.emptyactivity.data

data class Account(
    val name: String,
    val number: Int,
    val balance: Double,
    var dueDate: String? = null
)

data class Transaction(
    val date: String,
    val amount: Double,
    val detail: String,
    val subtotal: Double
)

var chequingTransactions = listOf(
    Transaction("2023-10-18", -23.00, "McDonald's", 1234.00),
    Transaction("2023-10-11", -7.00, "Couche-Tard", 1257.00),
    Transaction("2023-10-04", 500.00, "Transfer", 1264.00),
    Transaction("2023-09-27", -14.00, "Uniprix", 764.00),
    Transaction("2023-09-20", -46.00, "Gym", 778.00)
)

var savingsTransactions = listOf(
    Transaction("2023-10-18", 20.00, "Interest", 6789.00),
    Transaction("2023-10-11", -70.00, "Transfer", 6769.00),
    Transaction("2023-10-04", 20.00, "Interest", 6839.00),
    Transaction("2023-09-27", 400.00, "Deposit", 6819.00),
    Transaction("2023-09-20", 20.00, "Interest", 6419.00)
)

var creditTransactions = listOf(
    Transaction("2023-10-18", 549.00, "Rent", 999.00),
    Transaction("2023-10-11", 50.00, "GazMetro", 450.00),
    Transaction("2023-09-27", 400.00, "Tuition", 400.00)
)