package com.example.cjj.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Savings
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Date of retrieval: 2023/11/22
 * Class and objects are based on this GeeksForGeeks tutorial.
 * https://www.geeksforgeeks.org/bottom-navigation-bar-in-android-jetpack-compose/
 */
data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

/**
 * An object that contains a list of NavItems
 */
object Constants {
    val NavItems = listOf(
        NavItem(
            label = "Overview",
            icon = Icons.Filled.Home,
            route = "overview"
        ),
        NavItem(
            label = "Chequing",
            icon = Icons.Filled.AccountBalance,
            route = "chequing"
        ),
        NavItem(
            label = "Savings",
            icon = Icons.Filled.Savings,
            route = "savings"
        ),
        NavItem(
            label = "Credit",
            icon = Icons.Filled.CreditCard,
            route = "credit"
        )
    )
}