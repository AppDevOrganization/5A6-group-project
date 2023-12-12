package com.example.cjj

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

object ResetPswd : BankDestination {
    override val route = "resetpswd"
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

object AboutUs : BankDestination {
    override val route = "aboutus"
}

val bankTabRowScreens = listOf(Overview, Chequing, Savings, Credit,AboutUs)