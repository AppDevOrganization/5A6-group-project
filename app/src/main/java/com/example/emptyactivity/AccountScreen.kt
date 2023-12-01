package com.example.emptyactivity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.emptyactivity.data.Account
import com.example.emptyactivity.data.AccountType
import com.example.emptyactivity.data.Transaction

@Composable
fun AccountScreen(
    account: Account,
    onClickTransferButton: () -> Unit = {}
) {
    val balance = "$" + String.format("%.2f", account.balance)

    var screenDescription = "${account.type} screen, balance of $balance"

    if (account.type == AccountType.CREDIT) {
        screenDescription = "${account.type} screen, balance of $balance, due on ${account.dueDate}"
    }

    Column(
        modifier = Modifier
            .padding(13.dp)
            .semantics { contentDescription = screenDescription }
    ) {
        AccountTopCard(
            accountType = account.type,
            balance = account.balance,
            onClickTransferButton = onClickTransferButton
        )
        if (account.type == AccountType.CREDIT) {
            Text(
                text = "Due date: " + account.dueDate,
                modifier = Modifier.padding(13.dp),
                style = MaterialTheme.typography.displaySmall
            )
        }
        TransactionsLazyColumn(transactions = account.transactions)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsLazyColumn(
    transactions: MutableList<Transaction>
) {
    LazyColumn {
        item {
            TransactionsHeader()
        }
        items(transactions) {
            TransactionItem(
                transaction = it,
                modifier = Modifier.padding(2.dp)
            )
        }
    }
}

@Composable
fun TransactionsHeader()
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp),
            text = "Date",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            modifier = Modifier.weight(1f),
            text = "Amount",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            modifier = Modifier.weight(1f),
            text = "Transaction",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            modifier = Modifier.weight(1f),
            text = "Subtotal",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun TransactionItem(
    transaction: Transaction,
    modifier: Modifier = Modifier
) {
    val description = "${transaction.date}, ${transaction.amount} ${transaction.detail}, subtotal of ${transaction.subtotal}"

    Card {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp)
                .semantics {
                    contentDescription = description
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(7.dp)

        ) {
            Text(
                modifier = modifier.weight(1f),
                style = MaterialTheme.typography.bodySmall,
                text = transaction.date
            )
            Text(
                modifier = modifier.weight(1f),
                style = MaterialTheme.typography.bodySmall,
                text = "$" + String.format("%.2f", transaction.amount)
            )
            Text(
                modifier = modifier.weight(1f),
                style = MaterialTheme.typography.bodySmall,
                text = transaction.detail)
            Text(
                modifier = modifier.weight(1f),
                style = MaterialTheme.typography.bodySmall,
                text = "$" + String.format("%.2f", transaction.subtotal)
            )
        }
    }
    Spacer(modifier = modifier.size(1.dp))
}


@Composable
fun AccountTopCard(
    accountType: AccountType,
    balance: Double,
    onClickTransferButton: () -> Unit
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(7.dp)
            ) {
                Text(
                    text = accountType.name,
                    style = MaterialTheme.typography.displayMedium
                )
                Text(
                    text = "$" + String.format("%.2f", balance),
                    style = MaterialTheme.typography.displayLarge
                )
                Button(
                    onClick = onClickTransferButton
                ) {
                    if (accountType == AccountType.CREDIT) {
                        Text(
                            text = "Pay",
                            style = MaterialTheme.typography.titleLarge
                        )
                    } else {
                        Text(
                            text = "Transfer",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                var imageId = R.drawable.accounts
                if (accountType == AccountType.CHEQUING) {
                    /**
                     * Cheque icons created by srip - Flaticon
                     * https://www.flaticon.com/free-icons/cheque
                     */
                    imageId = R.drawable.cheque
                } else if (accountType == AccountType.SAVINGS) {
                    /**
                     * Piggy bank icons created by Freepik - Flaticon
                     * https://www.flaticon.com/free-icons/piggy-bank
                     */
                    imageId = R.drawable.piggy_bank
                } else if (accountType == AccountType.CREDIT) {
                    /**
                     * Credit card icons created by Freepik - Flaticon
                     * https://www.flaticon.com/free-icons/credit-card
                     */
                    imageId = R.drawable.credit_card
                }

                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp)
                )
            }
        }
    }
}

/*
@Preview
@Composable
fun AccountScreenPreview()
{
    val chequingAccount: Account? = chequingAccounts.find { it.number == 12345 }
    if (chequingAccount != null) {
        AccountScreen(account = chequingAccount)
    }
}

 */