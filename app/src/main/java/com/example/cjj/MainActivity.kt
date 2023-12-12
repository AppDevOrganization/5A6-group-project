package com.example.cjj

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cjj.components.Constants
import com.example.cjj.data.Account
import com.example.cjj.data.AccountType
import com.example.cjj.data.AccountsRepository
import com.example.cjj.data.TransactionsRepository
import com.example.cjj.data.UserPreferencesRepository
import com.example.cjj.home.OverviewScreen
import com.example.cjj.ui.theme.EmptyActivityTheme
import com.example.cjj.ui.theme.md_theme_dark_onPrimary
import com.example.cjj.ui.theme.md_theme_light_onPrimary
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.emptyactivity.data.AuthViewModel
import com.example.emptyactivity.data.AuthViewModelFactory

private const val USER_PREFERENCES_NAME = "user_preferences"

class MainActivity : ComponentActivity() {
    private val isDarkModeState = mutableStateOf(false)
    private val authViewModelFactory = AuthViewModelFactory()
    private lateinit var accountsViewModel: AccountsViewModel
    private lateinit var transactionsViewModel: TransactionsViewModel
    private val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME,
        produceMigrations = { context ->
            // Since we're migrating from SharedPreferences, add a migration based on the
            // SharedPreferences name
            listOf(SharedPreferencesMigration(context, USER_PREFERENCES_NAME))
        }
    )

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SuspiciousIndentation")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        accountsViewModel = ViewModelProvider(
            this,
            AccountsViewModelFactory(
                AccountsRepository,
                UserPreferencesRepository(dataStore = dataStore, context = this)
            )
        ).get(AccountsViewModel::class.java)

        transactionsViewModel = ViewModelProvider(
            this,
            TransactionsViewModelFactory(
                TransactionsRepository,
                UserPreferencesRepository(dataStore, this)
            )
        ).get(TransactionsViewModel::class.java)

        setContent {

            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                MainScreen(
                    modifier = Modifier, 
                    isDarkModeState = isDarkModeState, 
                    accountsViewModel = accountsViewModel, 
                    transactionsViewModel = transactionsViewModel,
                    authViewModelFactory = authViewModelFactory
                )
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
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier, 
    isDarkModeState: MutableState<Boolean>, 
    accountsViewModel: AccountsViewModel, 
    transactionsViewModel: TransactionsViewModel,
    authViewModelFactory: AuthViewModelFactory
) {


    var showStartupScreen by remember { mutableStateOf(true) }
    if (showStartupScreen) {
        LandingScreen(onTimeout = { showStartupScreen = false })
    } else {
        val navController = rememberNavController()

        CJJBankApp(
            navController = navController,
            isDarkModeState = isDarkModeState,
            modifier = modifier,
            accountsViewModel = accountsViewModel,
            transactionsViewModel = transactionsViewModel,
            authViewModelFactory = authViewModelFactory
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


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CJJBankApp(
    navController: NavHostController, 
    isDarkModeState: MutableState<Boolean>, 
    modifier: Modifier = Modifier, 
    accountsViewModel: AccountsViewModel, 
    transactionsViewModel: TransactionsViewModel,
    authViewModelFactory: AuthViewModelFactory
) {
    EmptyActivityTheme(
        useDarkTheme = isDarkModeState.value
    ) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen =
            bankTabRowScreens.find { it.route == currentDestination?.route } ?: Overview
        val authViewModel: AuthViewModel = viewModel(factory = authViewModelFactory)
        val userState = authViewModel.currentUser().collectAsState()
        var isFirstOpen by rememberSaveable { mutableStateOf(true) }

        if (isFirstOpen) {
            isFirstOpen = false
            authViewModel.signOut()
        }


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
                    accountsViewModel = accountsViewModel,
                    transactionsViewModel = transactionsViewModel,
                    authViewModel = authViewModel
                )
            }
        }


        if (!isOnStandalonePage(navController)) {
            if (userState.value == null) {
                navController.navigateSingleTopTo(Login.route)
            }

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
                            onClick = {
                                authViewModel.signOut()
                                navController.navigateSingleTopTo(Login.route)
                            }
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
                },
                modifier = Modifier.semantics {
                    onClick(label = "navigate to the ${item.route} screen", action = null)
                }
            )
        }
    }
}

/**
 * Date of retrieval: 2023/12/01
 * Displaying the account options in the dropdown menu.
 * https://www.geeksforgeeks.org/drop-down-menu-in-android-using-jetpack-compose/
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferScreen(
    viewModel: AccountsViewModel,
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

        var options = listOf(AccountType.CHEQUING, AccountType.SAVINGS, AccountType.CREDIT)

        // "From" account dropdown
        var fromAccount by remember { mutableStateOf(AccountType.NONE) }
        var isFromExpanded by remember { mutableStateOf(false) }
        var fromSelectedText by remember { mutableStateOf("") }
        var fromTextFieldSize by remember { mutableStateOf(Size.Zero) }

        val fromIcon = if (isFromExpanded) {
            Icons.Filled.KeyboardArrowUp
        } else {
            Icons.Filled.KeyboardArrowDown
        }

        Column {
            OutlinedTextField(
                value = fromSelectedText,
                onValueChange = { /* Do nothing */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        fromTextFieldSize = coordinates.size.toSize()
                    }
                    .padding(vertical = 8.dp),
                label = { Text("From Account") },
                readOnly = true, // Set the field to be read-only
                trailingIcon = {
                    Icon(
                        fromIcon, "contentDescription",
                        Modifier.clickable { isFromExpanded = !isFromExpanded })
                }
            )

            DropdownMenu(
                expanded = isFromExpanded,
                onDismissRequest = { isFromExpanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) {
                        fromTextFieldSize.width.toDp()
                    })
                    .padding(vertical = 8.dp)
            ) {
                options.forEach { label ->
                    DropdownMenuItem(onClick = {
                        fromSelectedText = label.name
                        fromAccount = label
                        isFromExpanded = false
                    }) {
                        Text(text = label.name)
                    }
                }
            }
        }

        // "To" account dropdown
        var toAccount by remember { mutableStateOf(AccountType.NONE) }
        var isToExpanded by remember { mutableStateOf(false) }
        var toSelectedText by remember { mutableStateOf("") }
        var toTextFieldSize by remember { mutableStateOf(Size.Zero) }

        val toIcon = if (isToExpanded) {
            Icons.Filled.KeyboardArrowUp
        } else {
            Icons.Filled.KeyboardArrowDown
        }

        Column {
            OutlinedTextField(
                value = toSelectedText,
                onValueChange = { /* Do nothing */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        toTextFieldSize = coordinates.size.toSize()
                    }
                    .padding(vertical = 8.dp),
                label = { Text("To Account") },
                readOnly = true, // Set the field to be read-only
                trailingIcon = {
                    Icon(
                        toIcon, "contentDescription",
                        Modifier.clickable { isToExpanded = !isToExpanded })
                }
            )

            DropdownMenu(
                expanded = isToExpanded,
                onDismissRequest = { isToExpanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) {
                        toTextFieldSize.width.toDp()
                    })
                    .padding(vertical = 8.dp)
            ) {
                options.forEach { account ->
                    DropdownMenuItem(onClick = {
                        toSelectedText = account.name
                        isToExpanded = false
                        toAccount = account
                    }) {
                        Text(text = account.name)

                    }
                }
            }
        }

        // Transfer amount input field
        var transferAmount by remember { mutableStateOf("") }
        OutlinedTextField(
            value = transferAmount,
            onValueChange = {
                // Handle value change and update the transferAmount variable
                transferAmount = it
            },
            label = { Text("Amount") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        var shouldShowMessage by remember { mutableStateOf(false) }
        var messageType by remember { mutableStateOf("") }
        var transferMessage by remember { mutableStateOf("") }

        if (shouldShowMessage) {
            TransferMessage(
                messageType = messageType,
                transferMessage = transferMessage,
                onDismissRequest = {
                    shouldShowMessage = false
                })
        }

        // Transfer button
        Button(
            onClick = {
                // Implement transfer logic here
                // validate input, perform the transfer, etc.
                var account : Account? = AccountsRepository.accounts.find { it.type == AccountType.CHEQUING }

                if (fromAccount == AccountType.CHEQUING) {
                    //account = viewModel.getAccountByType(AccountType.CHEQUING)
                    // for demo 4a
                    // to be replaced for final implementation 4b
                } else if (fromAccount == AccountType.SAVINGS) {
                    account = AccountsRepository.accounts.find { it.type == AccountType.SAVINGS }
                } else if (fromAccount == AccountType.CREDIT) {
                    account = AccountsRepository.accounts.find { it.type == AccountType.CREDIT }
                }

                var accountTransfer: Account? = AccountsRepository.accounts.find { it.type == AccountType.CHEQUING }

                if (toAccount == AccountType.CHEQUING) {


                } else if (toAccount == AccountType.SAVINGS) {
                    accountTransfer = AccountsRepository.accounts.find { it.type == AccountType.SAVINGS }
                } else if (toAccount == AccountType.CREDIT) {
                    accountTransfer = AccountsRepository.accounts.find { it.type == AccountType.CREDIT }
                }

                var transferAmountParsed = transferAmount.toDoubleOrNull()

                if (fromAccount == AccountType.NONE) {
                    messageType = "Error"
                    transferMessage = "Please select the account to transfer from."
                    shouldShowMessage = true
                } else if (toAccount == AccountType.NONE) {
                    messageType = "Error"
                    transferMessage = "Please select the account to transfer to."
                    shouldShowMessage = true
                } else if (fromAccount == toAccount) {
                    messageType = "Error"
                    transferMessage = "Cannot transfer funds to the same account."
                    shouldShowMessage = true
                } else if (transferAmountParsed == null) {
                    messageType = "Error"
                    transferMessage = "Transfer amount is not a valid number."
                    shouldShowMessage = true
                } else if (account != null) {
                    if (transferAmountParsed > account.balance) {
                        messageType = "Error"
                        transferMessage = "Transfer amount is more than available funds."
                        shouldShowMessage = true
                    } else {
                        messageType = "Success"
                        transferMessage = "Successfully transferred funds."
                        shouldShowMessage = true

                        if (accountTransfer != null) {

                            viewModel.transferFunds(account,accountTransfer,transferAmountParsed)
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Transfer")
        }
    }
}


@Composable
fun TransferMessage(
    messageType: String,
    transferMessage: String,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var imageId = 0

                if (messageType == "Error") {
                    /**
                     * Error icons created by IconBaandar - Flaticon
                     * https://www.flaticon.com/free-icons/error
                     */
                    imageId = R.drawable.error
                } else if (messageType == "Success") {
                    /**
                     * Tick icons created by Alfredo Hernandez - Flaticon
                     * https://www.flaticon.com/free-icons/tick
                     */
                    imageId = R.drawable.check
                }

                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp)
                )
                Text(
                    text = transferMessage
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text("Close")
                    }
                }
            }
        }
    }
}


/**
 * Date of Retrieval: 2023/11/02
 * All Nav-related functions and variables are based on the ones in the Rally app from the Navigation codelab.
 * https://developer.android.com/codelabs/jetpack-compose-navigation
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BankNavHost(
    navController: NavHostController,
    modifier: Modifier,
    accountsViewModel: AccountsViewModel,
    transactionsViewModel: TransactionsViewModel,
    authViewModel: AuthViewModel
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
                authViewModel = authViewModel,
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
                authViewModel = authViewModel,
                onClickLogin = {
                    navController.navigateSingleTopTo(Login.route)
                },
                onSuccess = {
                    navController.navigateSingleTopTo(Overview.route)
                }
            )
        }
        composable(route = ResetPswd.route) {
            ResetPasswordPage(
                authViewModel = authViewModel,
                goToLogin = {
                    navController.navigateSingleTopTo(Login.route)
                }
            )
        }
        composable(route = Chequing.route) {
            val chequingAccount = AccountsRepository.accounts.find { it.type == AccountType.CHEQUING }

            if (chequingAccount != null) {
                AccountScreen(
                    account = chequingAccount,
                    viewModel = transactionsViewModel,
                    onClickTransferButton = {
                        navController.navigateSingleTopTo(Transfer.route)
                    }
                )

            }
        }
        composable(route = Savings.route) {
            val savingsAccount = AccountsRepository.accounts.find { it.type == AccountType.SAVINGS }
            if (savingsAccount != null) {
                AccountScreen(
                    account = savingsAccount,
                    viewModel = transactionsViewModel,
                    onClickTransferButton = {
                        navController.navigateSingleTopTo(Transfer.route)
                    }
                )
            }
        }
        composable(route = Credit.route) {
            val creditAccount = AccountsRepository.accounts.find { it.type == AccountType.CREDIT }
            if (creditAccount != null) {
                AccountScreen(
                    account = creditAccount,
                    viewModel = transactionsViewModel,
                    onClickTransferButton = {
                        navController.navigateSingleTopTo(Transfer.route)
                    }
                )
            }
        }

        composable(route = Transfer.route) {
            TransferScreen(
                viewModel=accountsViewModel,
                onBackClick = {
                    navController.navigateSingleTopTo(Overview.route)
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