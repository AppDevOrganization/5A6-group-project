package com.example.emptyactivity

// Import necessary Compose components and libraries
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.lang.NumberFormatException

@Composable
fun StatefulAccount(modifier: Modifier = Modifier) {
    // Declare and initialize state variables for balance and transactions
    var balance by rememberSaveable { mutableStateOf(0) }
    var transactions by rememberSaveable { mutableStateOf("") }

    // Call the StatelessAccount composable with the state variables and modifier
    StatelessAccount(
            balance = balance,
            onDeposit = { depositAmount ->
                if (depositAmount > 0) {
                    balance += depositAmount
                    transactions += "\nDeposited $$depositAmount"
                }
            },
            onWithdraw = { withdrawAmount ->
                if (withdrawAmount in 1..balance) {
                    balance -= withdrawAmount
                    transactions += "\nWithdrawn $$withdrawAmount"
                }
            },
            transactions = transactions,
            modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatelessAccount(
        balance: Int,
        onDeposit: (depositAmount: Int) -> Unit,
        onWithdraw: (depositAmount: Int) -> Unit,
        transactions: String,
        modifier: Modifier = Modifier
) {
    // Column layout to arrange UI elements vertically
    Column(modifier = modifier.padding(16.dp)) {
        // Display the account balance
        Text("Account : $ $balance", fontSize = 30.sp)

        // State variables for user input and Snackbar visibility
        var userInput by remember { mutableStateOf("") }
        var showSnackbar by remember { mutableStateOf(false) }
        var showSnackbarTransactions by remember { mutableStateOf(false) }

        // Text input field for entering the transaction amount
        TextField(
                value = userInput,
                onValueChange = {
                    // Filter input to allow only digits
                    val newText = it.filter { it.isDigit() }
                    userInput = newText
                },
                label = { Text("Enter amount") },
                modifier = Modifier
                        .padding(top = 30.dp)
                        .fillMaxWidth()
        )

        // Row to arrange Deposit and Withdraw buttons
        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {
            // Deposit button
            Button(
                    onClick = {
                        onDeposit(userInput.toInt())
                    },
                    enabled = userInput != "",
                    modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Deposit")
            }

            // Withdraw button
            Button(
                    onClick = {
                        if (userInput.toInt() <= balance)
                            onWithdraw(userInput.toInt())
                        else
                            showSnackbar = true
                    },
                    enabled = userInput != "",
                    modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Withdraw")
            }
        }

        // Button to show transaction history
        Button(
                onClick = {
                    showSnackbarTransactions = true
                },
                modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Show Transactions")
        }

        // Snackbar for displaying transaction history
        if (showSnackbarTransactions) {
            Snackbar(
                    modifier = Modifier.padding(top = 16.dp),
                    action = {
                        Button(
                                onClick = { showSnackbarTransactions = false },
                                colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.onSecondary)
                        ) {
                            Text("Dismiss")
                        }
                    }
            ) {
                Text("\nTransaction History:\n$transactions")
            }
        }

        // Snackbar for insufficient balance message
        if (showSnackbar) {
            Snackbar(
                    modifier = Modifier.padding(top = 16.dp),
                    action = {
                        Button(
                                onClick = { showSnackbar = false },
                                colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.onSecondary)
                        ) {
                            Text("Dismiss")
                        }
                    }
            ) {
                Text("You don't have enough money to withdraw.")
            }
        }
    }
}
