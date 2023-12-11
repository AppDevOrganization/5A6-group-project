package com.example.cjj

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun SignupPage(
    onSuccess: () -> Unit = {},
    onClickLogin: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Create an Account",
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
        LoginSignupTextField(
            label = "Repeat Password",
            placeholder = "password",
            onValueChange = {},
        )
        Button(
            modifier = Modifier
                .padding(10.dp)
                .width(200.dp)
                .height(50.dp)
                .semantics {
                    onClick(label = "sign up for an account", action = null)
                },
            onClick = onSuccess
        ) {
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.titleLarge
            )
        }
        Button(
            modifier = Modifier
                .padding(10.dp)
                .width(300.dp)
                .height(50.dp)
                .semantics {
                    onClick(label = "return to the log in screen", action = null)
                },
            onClick = onClickLogin
        ) {
            Text(
                text = "Log in with an existing account",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview
@Composable
fun SignupPagePreview(modifier: Modifier = Modifier) {
    SignupPage(modifier = modifier)
}