package com.example.emptyactivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun SignupPage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Create an Account",
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
        LoginSignupTextField(
            placeholder = "Repeat Password",
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
                text = "Sign Up",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                text = "Don't have an account? Sign up"
            )
        }
    }
}

@Preview
@Composable
fun SignupPagePreview(modifier: Modifier = Modifier) {
    SignupPage(modifier)
}