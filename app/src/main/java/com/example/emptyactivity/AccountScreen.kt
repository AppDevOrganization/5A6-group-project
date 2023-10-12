package com.example.emptyactivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/*
Composable function called AccountScreen that defines the user interface for a screen in the app.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
        modifier: Modifier = Modifier
) {
    // Scaffold with top bar and bottom bar
    Scaffold(
            topBar = {
                // Top app bar with the title "BankTrode"
                TopAppBar(
                        title = { Text(text = "BankTrode") },
                )
            },
            content = { paddingValues ->
                // Content section with a gray background
                LazyColumn(
                        modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Gray)
                                .padding(paddingValues)
                ) {
                    items(1) { index ->
                        // Display the account information using the StatefulAccount composable
                        StatefulAccount()
                    }
                }
            },
            bottomBar = {
                // Bottom app bar
                BottomAppBar(
                        modifier = Modifier.fillMaxWidth(),
                        content = {
                            // Row in the bottom app bar with icons and buttons
                            Row(
                                    modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp)
                                            .background(MaterialTheme.colorScheme.primary),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                // IconButton for navigating to the home screen
                                IconButton(
                                        onClick = {
                                            /* Handle bottom app bar button click */
                                            /* No other screens so stays on home */
                                        },
                                        modifier = Modifier.size(48.dp)
                                ) {
                                    Icon(
                                            imageVector = Icons.Default.Home,
                                            contentDescription = "Home"
                                    )
                                }

                                // IconButton for exiting the app
                                IconButton(
                                        onClick = {
                                            // Exit the app by killing the process
                                            android.os.Process.killProcess(android.os.Process.myPid())
                                        },
                                        modifier = Modifier.size(48.dp)
                                ) {
                                    Icon(
                                            imageVector = Icons.Default.ExitToApp,
                                            contentDescription = "Exit"
                                    )
                                }
                            }
                        }
                )
            }
    )
}
