package com.example.emptyactivity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.emptyactivity.components.BankTabRow
import com.example.emptyactivity.ui.theme.EmptyActivityTheme

val SAMPLE_LIST = mutableListOf<Double>(5.01, 2.4, 6.4, 8.7, 5.9)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmptyActivityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                   //AccountScreen();
                   // Greeting("Android")
                    // WithdrawalScreen().ShowWithdrawalScreen()
                    // CadenPage().MainPage(breathList = SAMPLE_LIST)

MainScreen()


                }
            }
        }
    }
}


@Composable
fun MainScreen()
{
    var showStartupScreen by remember { mutableStateOf(true) }
    if (showStartupScreen) {
        LandingScreen(onTimeout = { showStartupScreen = false })
    } else {
        CJJBankApp()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CJJBankApp() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen = bankTabRowScreens.find { it.route == currentDestination?.route } ?: Overview

    Scaffold(
        topBar = {
            BankTabRow(
                screens = bankTabRowScreens,
                onTabSelected = { screen ->
                    navController.navigateSingleTopTo(screen.route)
                },
                currentScreen = currentScreen
            )
        }
    ) { contentPadding ->
        BankNavHost(
            navController = navController,
            modifier = Modifier.padding(contentPadding)
        )
    }
}

/**
 * Date of Retrieval: 2023/11/02
 * All Nav-related functions and variables are based on the ones in the Rally app from the Navigation codelab.
 * https://developer.android.com/codelabs/jetpack-compose-navigation
 */
@Composable
fun BankNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Overview.route,
        modifier = modifier
    ) {
        composable(route = Overview.route) {
            OverviewScreen(
                onClickViewChequingAccount = {
                    navController.navigateSingleTopTo(Chequing.route)
                }
            )
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

@Preview
@Composable
fun CJJBankAppPreview()
{
    CJJBankApp()
}

@Preview
@Composable
fun CJJBankAppDarkModePreview()
{
    EmptyActivityTheme(darkTheme = true) {
        CJJBankApp()
    }
}