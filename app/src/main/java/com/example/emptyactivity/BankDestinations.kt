package com.example.emptyactivity

interface BankDestination {
    val route: String
}

object Overview : BankDestination {
    override val route = "overview"
}

object Chequing : BankDestination {
    override val route = "chequing"
}

object Savings : BankDestination {
    override val route = "savings"
}

object Credit : BankDestination {
    override val route = "credit"
}

val bankTabRowScreens = listOf(Overview, Chequing, Savings, Credit)