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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.emptyactivity.data.AuthViewModel

@Composable
fun ResetPasswordPage(
    authViewModel: AuthViewModel,
    goToLogin: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val userState = authViewModel.currentUser().collectAsState()
    var emailText by rememberSaveable{ mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (userState.value == null) {
            Text(
                text = "Reset Password",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Type in your email address and we'll send a password reset request to it.",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            LoginSignupTextField(
                label = "Email",
                placeholder = "example@email.com",
                onValueChange = { emailText = it },
            )
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .width(200.dp)
                    .height(50.dp)
                    .semantics {
                        onClick(label = "Send password reset email.", action = null)
                    },
                onClick = {
                    authViewModel.sendPasswordResetEmail(emailText)
                }
            ) {
                Text(
                    text = "Send email",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .width(200.dp)
                    .height(50.dp)
                    .semantics {
                        onClick(label = "Go back to login page.", action = null)
                    },
                onClick = goToLogin
            ) {
                Text(
                    text = "Log in",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        } else {
            goToLogin()
        }
    }
}