package com.example.emptyactivity

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.emptyactivity.data.AuthViewModel
import com.google.android.play.integrity.internal.t


@Composable
fun SignupPage(
    authViewModel: AuthViewModel,
    onSuccess: () -> Unit = {},
    onClickLogin: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val userState = authViewModel.currentUser().collectAsState()
    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var passwordRepeatText by remember { mutableStateOf("") }
    val repeatPasswordField = @Composable { LoginSignupTextField(
        label = "Repeat Password",
        placeholder = "password",
        onValueChange = { passwordRepeatText = it },
        validate = {
            it == passwordText
        }
    ) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (userState.value == null) {
            Text(
                text = "Create an Account",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
            LoginSignupTextField(
                label = "Email",
                placeholder = "example@email.com",
                onValueChange = { emailText = it },
                validate = { authViewModel.validateEmail(it) }
            )
            LoginSignupTextField(
                label = "Password",
                placeholder = "password",
                onValueChange = { passwordText = it },
                validate = { authViewModel.validatePassword(it) }
            )
            repeatPasswordField()
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .width(200.dp)
                    .height(50.dp)
                    .semantics {
                        onClick(label = "sign up for an account", action = null)
                    },
                onClick = {
                    authViewModel.signUp(emailText, passwordText)
                }
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
        } else {
            onSuccess()
        }
    }
}

//@Preview
//@Composable
//fun SignupPagePreview(modifier: Modifier = Modifier) {
//    SignupPage(modifier = modifier)
//}