package com.example.emptyactivity.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Log In",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
        LoginSignupTextField(
            placeholder = "Email",
            onValueChange = {},
        )
        LoginSignupTextField(
            placeholder = "Password",
            onValueChange = {},
        )
        Button(
            modifier = Modifier
                .padding(10.dp)
                .width(200.dp)
                .height(50.dp),
            onClick = {}
        ) {
            Text(
                text = "Log in",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginSignupTextField(placeholder: String, onValueChange: (newValue: String) -> Unit, modifier: Modifier = Modifier) {
    var inputText by remember { mutableStateOf(placeholder) }

    OutlinedTextField(
        value = inputText,
        onValueChange = { inputText = it; onValueChange(inputText) },
        modifier = modifier.padding(5.dp)
    )
}

@Preview
@Composable
fun LoginPagePreview(modifier: Modifier = Modifier) {
    LoginPage(modifier)
}