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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.emptyactivity.data.AuthViewModel
import com.example.emptyactivity.data.ResultAuth
import com.google.android.play.integrity.internal.m

@Composable
fun ResetPasswordPage(
    authViewModel: AuthViewModel,
    goToLogin: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val RESET_ERROR = "Email is not of the valid format"//stringResource(id = R.string.login_error)
    val RESET_INTERNAL_ERROR = stringResource(id = R.string.signup_internal_error)

    val userState = authViewModel.currentUser().collectAsState()
    val resetPswdResult by authViewModel.resetPswdResult.collectAsState(ResultAuth.Inactive)

    var emailText by rememberSaveable{ mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var hasSentEmail by remember { mutableStateOf(false) }


    LaunchedEffect(resetPswdResult) {
        resetPswdResult?.let {
            if (it is ResultAuth.Inactive) {
                return@LaunchedEffect
            }
            if (it is ResultAuth.InProgress) {
                return@LaunchedEffect
            }
            if (it is ResultAuth.Success && it.data) {
                errorMessage = ""
                hasSentEmail = true
            }
            else if (it is ResultAuth.Failure || it is ResultAuth.Success) { // success(false) case
                if (it is ResultAuth.Failure)
                    errorMessage = RESET_INTERNAL_ERROR
                else if (it is ResultAuth.Success) {
                    errorMessage = RESET_ERROR
                }
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (userState.value == null) {
            if (!hasSentEmail) {
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
                if (errorMessage != "")
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.titleMedium
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
            }
            else {
                Text(
                    text = "Email Sent",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "We sent an email to ${emailText} to reset your account's password.",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
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