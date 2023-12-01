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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.emptyactivity.data.AuthViewModel
import com.example.emptyactivity.data.AuthViewModelFactory

@Composable
fun LoginPage(
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory()),
    onSuccess: () -> Unit = {},
    onClickSignup: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val userState = authViewModel.currentUser().collectAsState()
    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }


    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (userState.value == null) {
            Text(
                text = "Log In",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
            LoginSignupTextField(
                label = "Email",
                placeholder = "example@email.com",
                onValueChange = { emailText = it },
            )
            LoginSignupTextField(
                label = "Password",
                placeholder = "password",
                onValueChange = { passwordText = it },
            )
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .width(200.dp)
                    .height(50.dp)
                    .semantics {
                        onClick(label = "log in to your account", action = null)
                    },
                onClick = {
                    authViewModel.signIn(emailText, passwordText)
                }
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
                    .height(50.dp)
                    .semantics {
                        onClick(label = "create an account", action = null)
                    },
                onClick = onClickSignup
            ) {
                Text(
                    text = "Create an account",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        } else {
            onSuccess()
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
        singleLine = true,
        label = { Text(label) },
        value = inputText,
        onValueChange = { inputText = it; onValueChange(inputText) },
        placeholder = @Composable {
            Text(
                text = placeholder
            )
        },
        modifier = modifier
            .width(300.dp)
            .padding(5.dp)
    )
}

//@Preview
//@Composable
//fun LoginPagePreview(modifier: Modifier = Modifier) {
//    LoginPage(modifier = modifier)
//}