package com.example.cjj.data

import kotlinx.coroutines.flow.flowOf


object AccountsRepository{
    val accounts = flowOf(
        listOf(
                Account(type=AccountType.CHEQUING),


            Account(type=AccountType.SAVINGS
            ),

            Account(type=AccountType.CREDIT, dueDate = "2023-11-24"


        )
        )
    )

}
