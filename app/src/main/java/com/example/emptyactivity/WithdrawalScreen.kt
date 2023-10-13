package com.example.emptyactivity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        amount: String,
        modifier: Modifier = Modifier
    ) {
        Card(modifier = modifier) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                AmountInfo(amount)
            }
        }
    }

    @Composable
    fun AmountInfo(
        amount: String,
        modifier: Modifier = Modifier
    ) {
        Column(modifier = modifier) {
            Text(text = "$amount...")
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

    var amounts = listOf("$10", "$20", "$50", "$100")
}