package com.example.cjj

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.emptyactivity.data.AuthViewModel
import com.example.emptyactivity.data.ResultAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * The page where users log in.
 * @param authViewModel The AuthViewModel to authenticate the user's log in.
 * @param onSuccess The callback that gets called when the user has successfully logged in.
 * @param onClickSignup The callback that is supposed to take the user to the Sign Up page.
 * @param onClickResetPswd The callback that is supposed to take the user to the Reset Password page.
 */
@Composable
fun LoginPage(
    authViewModel: AuthViewModel,
    onSuccess: () -> Unit = {},
    onClickSignup: () -> Unit = {},
    onClickResetPswd: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val LOGIN_ERROR = stringResource(id = R.string.login_error)
    val LOGIN_INTERNAL_ERROR = stringResource(id = R.string.signup_internal_error)
    
    val userState = authViewModel.currentUser().collectAsState()
    val logInResult by authViewModel.logInResult.collectAsState(ResultAuth.Inactive)

    var emailText by rememberSaveable { mutableStateOf("") }
    var passwordText by rememberSaveable { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    var loginMessageShown by remember { mutableStateOf(false) }

    suspend fun showLoginMessage() {
        if (!loginMessageShown) {
            loginMessageShown = true
            delay(5000L)
            loginMessageShown = false
        }
    }

    val coroutineScope = rememberCoroutineScope()

    LoginMessage(loginMessageShown)

    LaunchedEffect(logInResult) {
        logInResult?.let {
            if (it is ResultAuth.Inactive) {
                return@LaunchedEffect
            }
            if (it is ResultAuth.InProgress) {
                return@LaunchedEffect
            }
            if (it is ResultAuth.Success && it.data) {

            }
            else if (it is ResultAuth.Failure || it is ResultAuth.Success) { // success(false) case
                if (it is ResultAuth.Failure)
                    errorMessage = LOGIN_INTERNAL_ERROR
                else if (it is ResultAuth.Success) {
                    errorMessage = LOGIN_ERROR
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
            /**
             * Login icons created by Uniconlabs - Flaticon
             * https://www.flaticon.com/free-icons/login
             */
            Image(
                painter = painterResource(id = R.drawable.login),
                contentDescription = null,
                modifier = Modifier
                    .size(110.dp)
                    .padding(8.dp)
            )
            Text(
                modifier = Modifier
                    .semantics {
                        contentDescription = "Fill out the email and password fields below to login to your account, or click the create an account button to sign up."
                    },
                text = "Log In",
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
                onValueChange = { emailText = it },
            )
            LoginSignupTextField(
                label = "Password",
                placeholder = "password",
                onValueChange = { passwordText = it },
                isPassword = true
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
                    coroutineScope.launch {
                        showLoginMessage()
                    }
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
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .width(240.dp)
                    .height(50.dp)
                    .semantics {
                        onClick(label = "Reset password", action = null)
                    },
                onClick = onClickResetPswd
            ) {
                Text(
                    text = "Forgot password?",
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
         * @param label The name of the field that appears on top of it.
         * @param placeholder The text field's placeholder text.
         * @param onValueChange The function that will be called with the new value passed in after
         * the text field's value gets changed.
         * @param validate The optional function that is supposed to validate the text field's value.
         * @param errorMessage The error message to display if the text field is invalid.
         * @param isPassword Whether the field is a password field or not. If true, the text field's value will be hidden, but there'll be a button to show it.
         */
fun LoginSignupTextField(
    label: String,
    placeholder: String,
    onValueChange: (newValue: String) -> Unit,
    modifier: Modifier = Modifier,
    validate: ((String) -> Boolean)? = null,
    errorMessage: String = "Error!",
    isPassword: Boolean = false
) {
    var inputText by rememberSaveable { mutableStateOf("") }
    var isValid by rememberSaveable { mutableStateOf(true) }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    if (validate != null)
        isValid = validate(inputText)

    OutlinedTextField(
        singleLine = true,
        label = { Text(label) },
        value = inputText,
        onValueChange = {
            inputText = it
            onValueChange(inputText)
             },
        placeholder = @Composable {
            Text(
                text = placeholder
            )
        },
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = {
                    isPasswordVisible = !isPasswordVisible
                }) {
                    Icon(
                        painter = painterResource(if (isPasswordVisible) R.drawable.password_visible else R.drawable.password_not_visible),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        },
        visualTransformation = if (isPassword && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        isError = inputText.isEmpty() || !isValid,
        modifier = modifier
            .width(300.dp)
            .padding(5.dp)
    )
    if (!isValid) {
        Text(
            text = errorMessage,
            color = Color.Red,
        )
    }
}

/**
 * Date of retrieval: 2023/12/14
 * Animating a login message as part of our advanced feature.
 * https://developer.android.com/codelabs/jetpack-compose-animation?hl=en#3
 */
@Composable
fun LoginMessage(
    visible: Boolean
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing),
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.tertiaryContainer,
            shadowElevation = 3.dp
        ) {
            Text(
                text = "Attempting to login to your account...",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

//@Preview
//@Composable
//fun LoginPagePreview(modifier: Modifier = Modifier) {
//    LoginPage(modifier = modifier)
//}