package com.example.emptyactivity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.emptyactivity.ui.theme.EmptyActivityTheme

val SAMPLE_LIST = mutableListOf<Double>(5.01, 2.4, 6.4, 8.7, 5.9)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmptyActivityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                   //AccountScreen();
                   // Greeting("Android")
                    // WithdrawalScreen().ShowWithdrawalScreen()
                    // CadenPage().MainPage(breathList = SAMPLE_LIST)
                    CJJBankApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CJJBankApp() {
    Scaffold(
        topBar = {
            CJJBankAppTopBar()
        }
    ) {
        contentPadding ->

        Box(modifier = Modifier.padding(contentPadding)) {
            OverviewScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CJJBankAppTopBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row {
                Text(
                    text = "OVERVIEW",
                    style = MaterialTheme.typography.labelLarge,
                )
                Spacer(modifier = Modifier.padding(13.dp))
                Text(
                    text = "CHEQUING",
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.padding(13.dp))
                Text(text = "SAVINGS",
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.padding(13.dp))
                Text(text = "CREDIT",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EmptyActivityTheme {
        Greeting("Android")
    }
}

@Preview
@Composable
fun CJJBankAppPreview()
{
    CJJBankApp()
}