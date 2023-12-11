package com.example.cjj.home

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
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.cjj.AccountsViewModel
import com.example.cjj.R
import com.example.cjj.data.AccountType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    viewModel: AccountsViewModel,
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
            viewModel,
            onClickViewAccount = onClickViewChequingAccount
        )
        SavingsAccountCard(
            viewModel,
            onClickViewAccount = onClickViewSavingsAccount
        )
        CreditAccountCard(
            viewModel,
            onClickViewAccount = onClickViewCreditAccount
        )
    }
}
@Composable
fun ChequingAccountCard(
    viewModel: AccountsViewModel,
    onClickViewAccount: () -> Unit
) {

    val chequingAccount= viewModel.getAccountByType(AccountType.CHEQUING)
    val balance = chequingAccount?.balance

    if (balance != null) {
        OverviewCard("Chequing", onClickViewAccount, balance)
    }
}

@Composable
fun SavingsAccountCard(
    viewModel: AccountsViewModel,
    onClickViewAccount: () -> Unit
) {
    val savingsAccount = viewModel.getAccountByType(AccountType.SAVINGS)
    val balance = savingsAccount?.balance

    if (balance != null) {
        OverviewCard("Savings", onClickViewAccount, balance)
    }
}

@Composable
fun CreditAccountCard(
    viewModel: AccountsViewModel,
    onClickViewAccount: () -> Unit
) {
    val creditAccount = viewModel.getAccountByType(AccountType.CREDIT)
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
            .semantics {
                onClick(label = "$accountType $balance", action = null)
            }
    ) {
        Column(
            modifier = Modifier.padding(13.dp)
        ) {
            Column(Modifier
                .fillMaxWidth()
                .semantics(mergeDescendants = true) {}) {
                Text(
                    text = accountType,
                    style = MaterialTheme.typography.displayMedium
                )
                Text(
                    text = "$" + String.format("%.2f", balance),
                    style = MaterialTheme.typography.displayLarge
                )
            }
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        onClick(label = "navigate to the $accountType screen", action = null)
                    },
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

/*
@Preview
@Composable
fun OverviewScreenPreview()
{
   var viewModel :AccountsViewModel

    OverviewScreen(viewModel)
}

@Preview
@Composable
fun OverviewScreenDarkModePreview() {
    EmptyActivityTheme(useDarkTheme = true) {
        OverviewScreen()
    }
}

 */