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
import androidx.compose.ui.unit.dp
import com.example.emptyactivity.data.AuthViewModel
import com.example.emptyactivity.data.ResultAuth
import com.google.android.play.integrity.internal.t

@Composable
fun SignupPage(
    authViewModel: AuthViewModel,
    onSuccess: () -> Unit = {},
    onClickLogin: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val SIGNUP_ERROR = stringResource(id = R.string.signup_error)
    val SIGNUP_INTERNAL_ERROR = stringResource(id = R.string.signup_internal_error)

    val userState = authViewModel.currentUser().collectAsState()
    val signUpResult by authViewModel.signUpResult.collectAsState(ResultAuth.Inactive)

    var emailText by rememberSaveable { mutableStateOf("") }
    var passwordText by rememberSaveable { mutableStateOf("") }
    var passwordRepeatText by rememberSaveable { mutableStateOf("") }
    val repeatPasswordField = @Composable { LoginSignupTextField(
        label = "Repeat Password",
        placeholder = "password",
        onValueChange = { passwordRepeatText = it },
        errorMessage = stringResource(id = R.string.signup_repeat_pswd_error),
        validate = {
            it == passwordText
        }
    ) }

    var errorMessage by remember { mutableStateOf("") }

    ///
    LaunchedEffect(signUpResult) {
        signUpResult?.let {
            if (it is ResultAuth.Inactive) {
                return@LaunchedEffect
            }
            if (it is ResultAuth.InProgress) {
                // TODO: Add some kind of text or something that shows that it's in progress
                return@LaunchedEffect
            }
            if (it is ResultAuth.Success && it.data) {
                
            }
            else if (it is ResultAuth.Failure || it is ResultAuth.Success) { // success(false) case
                if (it is ResultAuth.Failure)
                    errorMessage = SIGNUP_INTERNAL_ERROR
                else if (it is ResultAuth.Success)
                    errorMessage = SIGNUP_ERROR
            }
        }
    }


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
            if (errorMessage != "")
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.titleMedium
                )

            LoginSignupTextField(
                label = "Email",
                placeholder = "example@email.com",
                errorMessage = stringResource(id = R.string.signup_email_error),
                onValueChange = { emailText = it },
                validate = { authViewModel.validateEmail(it) }
            )
            LoginSignupTextField(
                label = "Password",
                placeholder = "password",
                errorMessage = stringResource(id = R.string.signup_pswd_error),
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