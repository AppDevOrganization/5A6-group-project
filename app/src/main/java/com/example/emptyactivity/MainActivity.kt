package com.example.emptyactivity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.emptyactivity.components.BankTabRow
import com.example.emptyactivity.data.Account
import com.example.emptyactivity.data.chequingAccounts
import com.example.emptyactivity.data.savingsAccounts
import com.example.emptyactivity.home.OverviewScreen
import com.example.emptyactivity.ui.theme.EmptyActivityTheme
import com.example.emptyactivity.ui.theme.md_theme_dark_background
import com.example.emptyactivity.ui.theme.md_theme_dark_onPrimary
import com.example.emptyactivity.ui.theme.md_theme_light_onPrimary
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val isDarkModeState = mutableStateOf(false)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {


              
                    //AccountScreen();
                    // Greeting("Android")
                    // WithdrawalScreen().ShowWithdrawalScreen()
                    // CadenPage().MainPage(breathList = SAMPLE_LIST)
                    MainScreen(isDarkModeState)
            }
        }
    }
}


/**
 * This is the main screen of the application that includes a Modal Navigation Drawer
 * with a set of navigation drawer items and a floating action button to control the drawer's state.
 *
 * @see https://developer.android.com/jetpack/compose/components/drawer for managing the state of the navigation drawer.
 * @param drawerState The state of the navigation drawer, manages whether it is open or closed.
 * @param scope A [CoroutineScope] used to launch coroutines for asynchronous operations.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(isDarkModeState : MutableState<Boolean>) {

    var showStartupScreen by remember { mutableStateOf(true) }
    if (showStartupScreen) {
        LandingScreen(onTimeout = { showStartupScreen = false })
    } else {


    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,

        drawerContent = {
            ModalDrawerSheet {
                // Title of the drawer
                Text("NAME HERE", modifier = Modifier.padding(16.dp))
                Divider()

                // Navigation drawer items
                NavigationDrawerItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "") },
                    label = { Text("Home") },
                    selected = false,
                    onClick = { /* Handle click for "Home" */ }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Filled.Send, contentDescription = "") },
                    label = { Text("Transfers") },
                    selected = false,
                    onClick = { /* Handle click for "Transfers" */ }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "") },
                    label = { Text("Settings") },
                    selected = false,
                    onClick = { /* Handle click for "Settings" */ }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Filled.Star, contentDescription = "") },
                    label = {
                        if(isDarkModeState.value)
                        {
                            Text("Light Mode")
                        }
                        else
                        {
                            Text("Dark Mode")
                        }

                            },
                    selected = false,
                    onClick = {  isDarkModeState.value = !isDarkModeState.value }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Filled.ExitToApp, contentDescription = "") },
                    label = { Text("Logout") },
                    selected = false,
                    onClick = { /* Handle click for "Logout" */ }
                )
            }
        },
    ) {
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("More") },
                    icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "") },
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
            }
        ) {
            it
            CJJBankApp(isDarkMode = isDarkModeState.value)
        }
    }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CJJBankApp(isDarkMode: Boolean) {
    EmptyActivityTheme(
        useDarkTheme = isDarkMode
    ) {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen = bankTabRowScreens.find { it.route == currentDestination?.route } ?: Overview

        var useDarkMode: Color = if(!isDarkMode) {
                    md_theme_light_onPrimary
                } else {
                    md_theme_dark_onPrimary
                }

        Scaffold(
            topBar = {
                BankTabRow(
                    screens = bankTabRowScreens,
                    onTabSelected = { screen ->
                        navController.navigateSingleTopTo(screen.route)
                    },
                    currentScreen = currentScreen,
                    backgroundColor = useDarkMode,
                    isDarkMode=  isDarkMode
                )
            }
        ) { contentPadding ->
            BankNavHost(
                navController = navController,
                modifier = Modifier.padding(contentPadding),
            )
        }
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
                },
                onClickViewSavingsAccount = {
                    navController.navigateSingleTopTo(Savings.route)
                },
                onClickViewCreditAccount = {
                    navController.navigateSingleTopTo(Credit.route)
                }
            )
        }
        composable(route = Chequing.route) {
            val chequingAccount: Account? = chequingAccounts.find { it.number == 12345 }
            if (chequingAccount != null) {
                AccountScreen(
                    account = chequingAccount,
                    onClickTransferButton = {
                        navController.navigateSingleTopTo(Transfer.route)
                    }
                )
            }
        }
        composable(route = Savings.route) {
            val savingsAccount: Account? = savingsAccounts.find { it.number == 12345 }
            if (savingsAccount != null) {
                AccountScreen(
                    account = savingsAccount,
                    onClickTransferButton = {
                        navController.navigateSingleTopTo(Transfer.route)
                    }
                )
            }
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
fun CJJBankAppPreview() {
    CJJBankApp(false)
}

@Preview
@Composable
fun CJJBankAppDarkModePreview()
{
    EmptyActivityTheme(useDarkTheme = true) {

        CJJBankApp(true)
    }
}