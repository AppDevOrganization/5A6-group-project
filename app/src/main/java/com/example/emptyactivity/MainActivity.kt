package com.example.emptyactivity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.DropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.emptyactivity.components.Constants
import com.example.emptyactivity.data.Account
import com.example.emptyactivity.data.chequingAccounts
import com.example.emptyactivity.data.creditAccounts
import com.example.emptyactivity.data.savingsAccounts
import com.example.emptyactivity.home.OverviewScreen
import com.example.emptyactivity.ui.theme.EmptyActivityTheme
import com.example.emptyactivity.ui.theme.md_theme_dark_background
import com.example.emptyactivity.ui.theme.md_theme_dark_onPrimary
import com.example.emptyactivity.ui.theme.md_theme_light_onPrimary
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
                MainScreen(modifier = Modifier, isDarkModeState)
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
fun MainScreen(modifier: Modifier, isDarkModeState: MutableState<Boolean>) {


    var showStartupScreen by remember { mutableStateOf(true) }
    if (showStartupScreen) {
        LandingScreen(onTimeout = { showStartupScreen = false })
    } else {
        val navController = rememberNavController()

       CJJBankApp(
           navController = navController,
           isDarkModeState = isDarkModeState,
           modifier = modifier
           )
    }
}

@Composable
fun DrawerHeader(modifier: Modifier, isDarkMode: Boolean) {

    var headerColor: Color = if (isDarkMode) {
        md_theme_dark_onPrimary
    } else {
        md_theme_light_onPrimary

    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .background(headerColor)
            .padding(15.dp)
            .fillMaxWidth()
    ) {

        Image(
            painterResource(id = R.drawable.cjjlogo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(70.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.padding(5.dp))

        Text(
            text = "CJJ",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
        )

        Text(
            text = "CJJ@gmail.com",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black,
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CJJBankApp(navController: NavHostController, isDarkModeState: MutableState<Boolean>, modifier: Modifier = Modifier) {
    EmptyActivityTheme(
        useDarkTheme = isDarkModeState.value
    ) {

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen =
            bankTabRowScreens.find { it.route == currentDestination?.route } ?: Overview

        var useDarkMode: Color = if (!isDarkModeState.value) {
            md_theme_light_onPrimary
        } else {
            md_theme_dark_onPrimary
        }

        val mainScaffold = @Composable {
            Scaffold(
                topBar = {
                    if (!isOnStandalonePage(navController)) {
                        NavigationBar(navController = navController)
                    }
                }
            ) { contentPadding ->
                BankNavHost(
                    navController = navController,
                    modifier = Modifier.padding(contentPadding),
                )
            }
        }


        if (!isOnStandalonePage(navController)) {
            ModalNavigationDrawer(
                drawerState = drawerState,
                modifier = Modifier,
                drawerContent = {
                    ModalDrawerSheet {
                        DrawerHeader(modifier, isDarkModeState.value)

                        Divider()

                        // Navigation drawer items
                        NavigationDrawerItem(
                            icon = { Icon(Icons.Filled.Home, contentDescription = "") },
                            label = { Text("Home") },
                            selected = false,
                            onClick = {
                                navController.navigateSingleTopTo(Overview.route)
                                scope.launch {
                                    if (drawerState.isClosed) {
                                        drawerState.open()
                                    } else {
                                        drawerState.close()
                                    }
                                }
                            }
                        )
                        NavigationDrawerItem(
                            icon = { Icon(Icons.Filled.Send, contentDescription = "") },
                            label = { Text("Transfers") },
                            selected = false,
                            onClick = {
                                navController.navigateSingleTopTo(Transfer.route)
                                scope.launch {
                                    if (drawerState.isClosed) {
                                        drawerState.open()
                                    } else {
                                        drawerState.close()
                                    }


                                }
                            }
                        )
                        NavigationDrawerItem(
                            icon = { Icon(Icons.Filled.Star, contentDescription = "") },
                            label = {
                                if (isDarkModeState.value) {
                                    Text("Light Mode")
                                } else {
                                    Text("Dark Mode")
                                }

                            },
                            selected = false,
                            onClick = { isDarkModeState.value = !isDarkModeState.value }
                        )
                        NavigationDrawerItem(
                            icon = { Icon(Icons.Filled.Settings, contentDescription = "") },
                            label = { Text("Settings") },
                            selected = false,
                            onClick = { /* Handle click for "Settings" */ }
                        )
                        NavigationDrawerItem(
                            icon = { Icon(Icons.Filled.ExitToApp, contentDescription = "") },
                            label = { Text("Logout") },
                            selected = false,
                            onClick = { navController.navigateSingleTopTo(Login.route) }
                        )
                    }
                }
            ) {
                Scaffold(
                    floatingActionButton = {
                        ExtendedFloatingActionButton(
                            text = { Text("More") },
                            icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "") },
                            onClick = {
                                if (!isOnStandalonePage(navController)) {
                                    scope.launch {
                                        drawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                }
                            }
                        )
                    }
                ) {
                    it
//                CJJBankApp(navController = navController, isDarkMode = isDarkModeState.value)
                    mainScaffold()
                }
            }
        } else {
            mainScaffold()
        }
    }
}

/**
 * Date of retrieval: 2023/11/22
 * This function is based on this GeeksForGeeks tutorial.
 * https://www.geeksforgeeks.org/bottom-navigation-bar-in-android-jetpack-compose/
 */
@Composable
fun NavigationBar(
    navController: NavHostController
) {
    BottomNavigation(
        backgroundColor = Color(android.graphics.Color.rgb(51, 153, 102))
    ) {
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStack?.destination?.route

        Constants.NavItems.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigateSingleTopTo(item.route)
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferScreen(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back button
        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .clickable { onBackClick() }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .padding(4.dp)
            )
        }

        // Transfer details
        Text(
            text = "Make a transfer",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // "From" account dropdown
        var fromAccount by remember { mutableStateOf("Chequing") }
        DropdownMenu(
            expanded = false, // Use a state variable to control the expanded state
            onDismissRequest = { /* Handle dismiss request if needed */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            // Populate the dropdown menu with account options
            listOf("Chequing", "Savings").forEach { accountType ->
                DropdownMenuItem(
                    onClick = {
                        fromAccount = accountType
                    }
                ) {
                    Text(accountType)
                }
            }
        }
        OutlinedTextField(
            value = fromAccount,
            onValueChange = {},
            label = { Text("From Account") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // "To" account dropdown
        var toAccount by remember { mutableStateOf("Savings") }
        DropdownMenu(
            expanded = false, // Use a state variable to control the expanded state
            onDismissRequest = { /* Handle dismiss request if needed */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            // Populate the dropdown menu with account options
            listOf("Chequing", "Savings").forEach { accountType ->
                DropdownMenuItem(
                    onClick = {
                        toAccount = accountType
                    },
                    modifier = Modifier.fillMaxWidth() // Add this line to fill the width of the menu item
                ) {
                    Text(accountType)
                }
            }
        }
        OutlinedTextField(
            value = toAccount,
            onValueChange = {},
            label = { Text("To Account") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Transfer amount input field
        var transferAmount by remember { mutableStateOf(0.0) }
        OutlinedTextField(
            value = transferAmount.toString(),
            onValueChange = {
                // Handle value change and update the transferAmount variable
                transferAmount = it.toDoubleOrNull() ?: 0.0
            },
            label = { Text("Amount") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Transfer button
        Button(
            onClick = {
                // Implement transfer logic here
                // validate input, perform the transfer, etc.

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Transfer")
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
        startDestination = Login.route,
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
        composable(route = Login.route) {
            LoginPage(
                onClickSignup = {
                    navController.navigateSingleTopTo(Signup.route)
                },
                onSuccess = {
                    navController.navigateSingleTopTo(Overview.route)
                }
            )
        }
        composable(route = Signup.route) {
            SignupPage(
                onClickLogin = {
                    navController.navigateSingleTopTo(Login.route)
                },
                onSuccess = {
                    navController.navigateSingleTopTo(Overview.route)
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
        composable(route = Credit.route) {
            val creditAccount: Account? = creditAccounts.find { it.number == 12345 }
            if (creditAccount != null) {
                AccountScreen(
                    account = creditAccount,
                    onClickTransferButton = {
                        navController.navigateSingleTopTo(Transfer.route)
                    }
                )
            }
        }

        composable(route = Transfer.route) {
            TransferScreen(onBackClick = {
                navController.navigateUp()
            })
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

fun isOnStandalonePage(navController: NavHostController): Boolean {
    val currentRoute = navController.currentDestination?.route
    return currentRoute == null || (currentRoute == Login.route || currentRoute == Signup.route)
}

//@Preview
//@Composable
//fun CJJBankAppPreview() {
//    val navController = rememberNavController()
//    CJJBankApp(navController = navController, false)
//}
//
//@Preview
//@Composable
//fun CJJBankAppDarkModePreview() {
//    EmptyActivityTheme(useDarkTheme = true) {
//
//        val navController = rememberNavController()
//        CJJBankApp(navController = navController, true)
//    }
//}