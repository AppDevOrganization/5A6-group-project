package com.example.cjj.data


/**
 * Represents an object that contains every account type, as well as their balance and transactions.
 */
object AccountsRepository{
    val accounts = listOf(
        Account(
            type = AccountType.CHEQUING,
            balance = 1364.00,
            transactions = TransactionsRepository.chequingTransactions
        ),
        Account(
            type = AccountType.SAVINGS,
            balance = 6719.00,
            transactions = TransactionsRepository.savingsTransactions
        ),
        Account(
            type = AccountType.CREDIT,
            balance = 999.00,
            dueDate = "2023-11-24",
            transactions = TransactionsRepository.creditTransactions
        )
    )
}
