package com.example.emptyactivity

interface BankDestination {
    val route: String
}

object Overview : BankDestination {
    override val route = "overview"
}

object Login : BankDestination {
    override val route = "login"
}

object Signup : BankDestination {
    override val route = "signup"
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

object Transfer : BankDestination {
    override val route = "transfer"
}

val bankTabRowScreens = listOf(Overview, Chequing, Savings, Credit)