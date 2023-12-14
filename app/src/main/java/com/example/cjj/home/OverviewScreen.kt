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
import com.example.cjj.R
import com.example.cjj.data.AccountType
import com.example.cjj.data.AccountsRepository

/**
 * The screen where all accounts are listed in summary format.
 * @param onClickViewChequingAccount The callback that will be called when the user wants to view the chequing page.
 * @param onClickViewSavingsAccount The callback that will be called when the user wants to view the savings page.
 * @param onClickViewCreditAccount The callback that will be called when the user wants to view the credit page.
 */
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

/**
 * A card formatted summary of the chequing account.
 * @param onClickViewAccount The callback that will be called when the user clicks on this card.
 */
@Composable
fun ChequingAccountCard(
    onClickViewAccount: () -> Unit
) {

    val chequingAccount= AccountsRepository.accounts.find { it.type == AccountType.CHEQUING }
    val balance = chequingAccount?.balance

    if (balance != null) {
        OverviewCard("Chequing", onClickViewAccount, balance)
    }
}

/**
 * A card formatted summary of the savings account.
 * @param onClickViewAccount The callback that will be called when the user clicks on this card.
 */
@Composable
fun SavingsAccountCard(
    onClickViewAccount: () -> Unit
) {
    val savingsAccount = AccountsRepository.accounts.find { it.type == AccountType.SAVINGS }
    val balance = savingsAccount?.balance

    if (balance != null) {
        OverviewCard("Savings", onClickViewAccount, balance)
    }
}

/**
 * A card formatted summary of the credit account.
 * @param onClickViewAccount The callback that will be called when the user clicks on this card.
 */
@Composable
fun CreditAccountCard(
    onClickViewAccount: () -> Unit
) {
    val creditAccount = AccountsRepository.accounts.find { it.type == AccountType.CREDIT }
    val balance = creditAccount?.balance

    if (balance != null) {
        OverviewCard("Credit", onClickViewAccount, balance)
    }
}

/**
 * A card template used for a summary/overview of a specific account
 * @param accountType The type of account.
 * @param onClickViewAccount The callback that will be called when the user clicks on this card.
 * @param balance The balance of the account.
 */
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
