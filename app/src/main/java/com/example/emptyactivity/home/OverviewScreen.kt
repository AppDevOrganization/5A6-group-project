package com.example.emptyactivity.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.emptyactivity.R
import com.example.emptyactivity.data.Account
import com.example.emptyactivity.data.chequingAccounts
import com.example.emptyactivity.data.creditAccounts
import com.example.emptyactivity.data.savingsAccounts
import com.example.emptyactivity.ui.theme.EmptyActivityTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    onClickViewChequingAccount: () -> Unit = {},
    onClickViewSavingsAccount: () -> Unit = {},
    onClickViewCreditAccount: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(13.dp)
            .semantics { contentDescription = "Overview Screen" }
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            /**
             * Accounting icons created by Freepik - Flaticon
             * https://www.flaticon.com/free-icons/accounting
             */
            Image(
                painter = painterResource(id = R.drawable.accounts),
                contentDescription = null,
                modifier = Modifier
                    .size(91.dp)
                    .padding(8.dp)
            )
        }
        ChequingAccountCard(
            onClickViewAccount = onClickViewChequingAccount
        )
        SavingsAccountCard(
            onClickViewAccount = onClickViewSavingsAccount
        )
        CreditAccountCard(
            onClickViewAccount = onClickViewCreditAccount
        )
    }
}

@Composable
fun ChequingAccountCard(
    onClickViewAccount: () -> Unit
) {

    val chequingAccount: Account? = chequingAccounts.find { it.number == 12345 }
    val balance = chequingAccount?.balance

    if (balance != null) {
        OverviewCard("Chequing", onClickViewAccount, balance)
    }
}

@Composable
fun SavingsAccountCard(
    onClickViewAccount: () -> Unit
) {
    val savingsAccount: Account? = savingsAccounts.find { it.number == 12345 }
    val balance = savingsAccount?.balance

    if (balance != null) {
        OverviewCard("Savings", onClickViewAccount, balance)
    }
}

@Composable
fun CreditAccountCard(
    onClickViewAccount: () -> Unit
) {
    val creditAccount: Account? = creditAccounts.find { it.number == 12345 }
    val balance = creditAccount?.balance

    if (balance != null) {
        OverviewCard("Credit", onClickViewAccount, balance)
    }
}

@Composable
fun OverviewCard(
    accountType: String,
    onClickViewAccount: () -> Unit,
    balance: Double
) {

    Card (
        modifier = Modifier
            .padding(13.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(13.dp)
        ) {
            Text(
                text = accountType,
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                text = "$" + String.format("%.2f", balance),
                style = MaterialTheme.typography.displayLarge
            )
            TextButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = onClickViewAccount
            ) {
                Text(
                    text = "SEE MORE",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun OverviewScreenPreview()
{
    OverviewScreen()
}

@Preview
@Composable
fun OverviewScreenDarkModePreview() {
    EmptyActivityTheme(useDarkTheme = true) {
        OverviewScreen()
    }
}