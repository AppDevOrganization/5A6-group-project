package com.example.cjj

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.cjj.data.Account
import com.example.cjj.data.AccountType
import com.example.cjj.data.Transaction

/**
 * The screen that displays all the information about a specific account.
 * @param account The account that needs to be displayed.
 * @param viewModel The ViewModel for transactions.
 * @param onClickTransferButton A callback that is called when the user wants to transfer money from this account.
 */
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AccountScreen(
    account: Account,
    viewModel: TransactionsViewModel,
    onClickTransferButton: () -> Unit = {}
) {
    val balance = "$" + String.format("%.2f", account.balance)

    var screenDescription = "${account.type} screen, balance of $balance"
    var transactions = viewModel.chequingTransactions

    if (account.type == AccountType.SAVINGS) {
        transactions = viewModel.savingsTransactions
    }

    if (account.type == AccountType.CREDIT) {
        screenDescription = "${account.type} screen, balance of $balance, due on ${account.dueDate}"
        transactions = viewModel.creditTransactions
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

        TransactionsLazyColumn(accountType = account.type, viewModel = viewModel, transactions = transactions)
    }
}

/**
 * A lazy column made to display all the transactions in an account.
 * @param accountType The type of account.
 * @param viewModel The ViewModel for transactions.
 * @param transactions A list of transactions for this account.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsLazyColumn(
    accountType: AccountType,
    viewModel: TransactionsViewModel,
    transactions: List<Transaction>?
) {
    var sortByDate by rememberSaveable { mutableStateOf(false) }
    var sortByDateText by rememberSaveable { mutableStateOf("Sort date") }
    if (sortByDate) {
        sortByDateText = "✅ Sort date"
    } else {
        sortByDateText = "Sort date"
    }

    var sortByAZ by rememberSaveable { mutableStateOf(false) }
    var sortByAZText by rememberSaveable { mutableStateOf("Sort A→Z") }
    if (sortByAZ) {
        sortByAZText = "✅ Sort A→Z"
    } else {
        sortByAZText = "Sort A→Z"
    }

    /**
     * Date of retrieval: 2023/12/14
     * Animating color change as part of our advanced feature.
     * https://developer.android.com/codelabs/jetpack-compose-animation?hl=en#2
     */
    val buttonColorDate by animateColorAsState(
        targetValue = if (sortByDate) Color.DarkGray else Color.Gray,
        label = "date button color"
    )

    val buttonColorAZ by animateColorAsState(
        targetValue = if (sortByAZ) Color.DarkGray else Color.Gray,
        label = "AZ button color"
    )

    Row(
        Modifier
            .fillMaxWidth()
            .padding(2.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            modifier = Modifier
                .semantics {
                    onClick(label = "sort the list of transactions by date", action = null)
                },
            onClick = {
                sortByDate = sortByDate == false
                viewModel.enableSortByDate(sortByDate, accountType)
            },
            colors = ButtonDefaults.buttonColors(containerColor = buttonColorDate)
        ) {
            Text(
                text = sortByDateText,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Button(
            modifier = Modifier
                .semantics {
                    onClick(label = "sort the list of transactions in alphabetical order", action = null)
                },
            onClick = {
                sortByAZ = sortByAZ == false
                viewModel.enableSortAlphabetically(sortByAZ, accountType)
            },
            colors = ButtonDefaults.buttonColors(containerColor = buttonColorAZ)
        ) {
            Text(
                text = sortByAZText,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }

    LazyColumn {
        item {
            TransactionsHeader()
        }
        if (transactions != null) {
            items(transactions) {
                TransactionItem(
                    transaction = it,
                    modifier = Modifier.padding(2.dp)
                )
            }
        }

    }
}

/**
 * The header for a list of transactions.
 */
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

/**
 * An item in a list of transactions.
 * @param transaction The individual transaction.
 */
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
                text = transaction.date.toString()
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


/**
 * The card formatted display of an account's general information.
 * @param accountType The type of account.
 * @param balance The money balance in the account.
 * @param onClickTransferButton The callback that will be called when the user wants to transfer money from this account.
 */
@Composable
fun AccountTopCard(
    accountType: AccountType,
    balance: Double,
    onClickTransferButton: () -> Unit
) {
    var topButtonDescription = "make a transfer between accounts"

    if (accountType == AccountType.CREDIT) {
        topButtonDescription = "make a payment on your credit card balance"
    }

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
                    modifier = Modifier
                        .semantics {
                            onClick(label = topButtonDescription, action = null)
                        },
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
