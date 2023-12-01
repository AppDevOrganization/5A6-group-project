package com.example.emptyactivity.data

enum class AccountType
{
    CHEQUING,
    SAVINGS,
    CREDIT
}

data class Account(
    val name: String,
    val number: Int,
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

var chequingAccounts = listOf(
    Account(name = "Chequing", number = 12345, balance = 1364.00, transactions = mutableListOf(
        Transaction("2023-11-22", 100.00, "Transfer", 1364.00),
        Transaction("2023-11-15", -13.00, "Boston Pizza", 1264.00),
        Transaction("2023-11-08", -3.00, "Depanneur 123", 1277.00),
        Transaction("2023-11-01", -14.00, "Jean Coutu", 1280.00),
        Transaction("2023-10-25", 60.00, "Transfer", 1294.00),
        Transaction("2023-10-18", -23.00, "McDonald's", 1234.00),
        Transaction("2023-10-11", -7.00, "Couche-Tard", 1257.00),
        Transaction("2023-10-04", 500.00, "Transfer", 1264.00),
        Transaction("2023-09-27", -14.00, "Uniprix", 764.00),
        Transaction("2023-09-20", -46.00, "Gym", 778.00))
    )
)

var savingsAccounts = listOf(
    Account(name = "Savings", number = 12345, balance = 6719.00, transactions = mutableListOf(
        Transaction("2023-11-22", -100.00, "Transfer", 6719.00),
        Transaction("2023-11-15", 20.00, "Interest", 6819.00),
        Transaction("2023-11-08", 50.00, "Deposit", 6799.00),
        Transaction("2023-11-01", 20.00, "Interest", 6749.00),
        Transaction("2023-10-25", -60.00, "Transfer", 6729.00),
        Transaction("2023-10-18", 20.00, "Interest", 6789.00),
        Transaction("2023-10-11", -70.00, "Transfer", 6769.00),
        Transaction("2023-10-04", 20.00, "Interest", 6839.00),
        Transaction("2023-09-27", 400.00, "Deposit", 6819.00),
        Transaction("2023-09-20", 20.00, "Interest", 6419.00))
    )
)

var creditAccounts = listOf(
    Account(name = "Credit", number = 12345, balance = 999.00, dueDate = "2023-11-24",
        transactions = mutableListOf(
            Transaction("2023-10-18", 549.00, "Rent", 999.00),
            Transaction("2023-10-11", 50.00, "GazMetro", 450.00),
            Transaction("2023-09-27", 400.00, "Tuition", 400.00)
        )
    )
)