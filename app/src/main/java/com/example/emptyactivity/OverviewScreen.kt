package com.example.emptyactivity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.emptyactivity.data.Account
import com.example.emptyactivity.data.chequingAccounts
import com.example.emptyactivity.ui.theme.EmptyActivityTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    onClickViewChequingAccount: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(13.dp)
            .semantics { contentDescription = "Overview Screen" }
            .verticalScroll(rememberScrollState())
    ) {
        ChequingAccountCard(
            onClickViewAccount = onClickViewChequingAccount
        )
    }
}

@Composable
fun ChequingAccountCard(
    onClickViewAccount: () -> Unit
) {

    val chequingAccount: Account? = chequingAccounts.find { it.number == 12345 }
    val balance = chequingAccount?.balance

    Card (
        modifier = Modifier
            .padding(13.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Chequing",
            style = MaterialTheme.typography.displaySmall
        )
        Text(
            text = "$$balance",
            style = MaterialTheme.typography.displayLarge
        )
        TextButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onClickViewAccount
        ) {
            Text(
                text = "VIEW",
                style = MaterialTheme.typography.titleLarge
            )
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
    EmptyActivityTheme(darkTheme = true) {
        OverviewScreen()
    }
}