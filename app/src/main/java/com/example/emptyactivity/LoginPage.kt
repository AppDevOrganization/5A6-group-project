package com.example.emptyactivity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
fun LoginPage(
    onSuccess: () -> Unit = {},
    onClickSignup: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Log In",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
        LoginSignupTextField(
            label = "Email",
            placeholder = "example@email.com",
            onValueChange = {},
        )
        LoginSignupTextField(
            label = "Password",
            placeholder = "password",
            onValueChange = {},
        )
        Button(
            modifier = Modifier
                .padding(10.dp)
                .width(200.dp)
                .height(50.dp),
            onClick = onSuccess
        ) {
            Text(
                text = "Log in",
                style = MaterialTheme.typography.titleLarge
            )
        }
        Button(
            modifier = Modifier
                .padding(10.dp)
                .width(240.dp)
                .height(50.dp),
            onClick = onClickSignup
        ) {
            Text(
                text = "Create an account",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
        /**
         * A text field for a user's email, password, etc. As the name implies, it should only be used for the
         * login and signup pages.
         * @param placeholder The text field's placeholder text.
         * @param onValueChange The function that will be called with the new value passed in after
         * the text field's value gets changed.
         */
fun LoginSignupTextField(label: String, placeholder: String, onValueChange: (newValue: String) -> Unit, modifier: Modifier = Modifier) {
    var inputText by remember { mutableStateOf("") }

    OutlinedTextField(
        label = { Text(label) },
        value = inputText,
        onValueChange = { inputText = it; onValueChange(inputText) },
        placeholder = @Composable {
            Text(
                text = placeholder
            )
        },
        modifier = modifier.padding(5.dp)
    )
}

@Preview
@Composable
fun LoginPagePreview(modifier: Modifier = Modifier) {
    LoginPage(modifier = modifier)
}