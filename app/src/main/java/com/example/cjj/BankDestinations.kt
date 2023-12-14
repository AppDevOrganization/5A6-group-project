package com.example.cjj

/**
 * Represents a destination for a page in the app.
 */
interface BankDestination {
    val route: String
}

/**
 * The destination of the Overview page.
 */
object Overview : BankDestination {
    override val route = "overview"
}

/**
 * The destination of the Login page.
 */
object Login : BankDestination {
    override val route = "login"
}

/**
 * The destination of the Signup page.
 */
object Signup : BankDestination {
    override val route = "signup"
}

/**
 * The destination of the Reset Password page.
 */
object ResetPswd : BankDestination {
    override val route = "resetpswd"
}

/**
 * The destination of the Chequing page.
 */
object Chequing : BankDestination {
    override val route = "chequing"
}

/**
 * The destination of the Savings page.
 */
object Savings : BankDestination {
    override val route = "savings"
}

/**
 * The destination of the Credit page.
 */
object Credit : BankDestination {
    override val route = "credit"
}

/**
 * The destination of the Transfer page.
 */
object Transfer : BankDestination {
    override val route = "transfer"
}

/**
 * The destination of the About Us page.
 */
object AboutUs : BankDestination {
    override val route = "aboutus"
}

val bankTabRowScreens = listOf(Overview, Chequing, Savings, Credit,AboutUs)