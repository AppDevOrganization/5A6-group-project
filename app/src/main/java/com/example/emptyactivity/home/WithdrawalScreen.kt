package com.example.emptyactivity.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.emptyactivity.R
import com.example.emptyactivity.ui.theme.EmptyActivityTheme

class WithdrawalScreen {


    /**
     * The main application for the Bank App.
     */
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ShowWithdrawalScreen() {


        Scaffold(
            topBar = {
                WithdrawalScreenTopBar()
            }
        ) { padding ->
            LazyColumn(contentPadding = padding) {
                item {
                    SectionHeader(
                        /**
                         * Withdraw icons created by nangicon - Flaticon
                         * https://www.flaticon.com/free-icons/withdraw
                         */
                        imageId = R.drawable.withdrawal,
                        text = "Withdraw"
                    )
                }
                items(amounts) {
                    AmountItem(
                        amount = it,
                        modifier = Modifier.padding(2.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun SectionHeader(
        imageId: Int,
        text: String,
        modifier: Modifier = Modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = null,
                modifier = modifier
                    .size(64.dp)
                    .padding(8.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.displayMedium
            )
        }
    }

    @Composable
    fun AmountItem(
        amount: Int,
        modifier: Modifier = Modifier
    ) {
        var shouldShowReceipt by remember { mutableStateOf(false) }

        Button(
            onClick = { shouldShowReceipt = true },
            colors = ButtonDefaults
                .buttonColors(
                    containerColor = Color(0xff006699),
                    contentColor = Color.White),
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "$$amount")
        }

        if (shouldShowReceipt) {
            ReceiptDialog(amount, onDismissRequest = { shouldShowReceipt = false })
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun WithdrawalScreenTopBar(modifier: Modifier = Modifier) {
        CenterAlignedTopAppBar(
            modifier = modifier,
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    /**
                     * Bank icons created by Freepik - Flaticon
                     * https://www.flaticon.com/free-icons/bank
                     */
                    Image(
                        modifier = Modifier
                            .size(64.dp)
                            .padding(8.dp),
                        painter = painterResource(id = R.drawable.bank),
                        contentDescription = null
                    )
                    Text(
                        text = "Bank App",
                        style = MaterialTheme.typography.displayLarge
                    )
                }
            }
        )
    }

    /**
     * Displays a preview of the application.
     */
    @Preview
    @Composable
    fun WithdrawalScreenPreview() {
        EmptyActivityTheme {
            ShowWithdrawalScreen()
        }
    }

    @Composable
    fun ReceiptDialog(
        amount: Int,
        onDismissRequest: () -> Unit
    ) {
        Dialog(onDismissRequest = { onDismissRequest() }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Successfully withdrew $$amount"
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

    var amounts = listOf(10, 20, 50, 100)
}