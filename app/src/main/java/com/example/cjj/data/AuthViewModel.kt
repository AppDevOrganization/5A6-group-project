package com.example.emptyactivity.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository): ViewModel() {
    /**
     * Returns a StateFlow of the current logged in user.
     * @return A StateFlow of the current logged in user.
     */
    fun currentUser(): StateFlow<User?> {
        return authRepository.currentUser()
    }

    private val _signUpResult = MutableStateFlow<ResultAuth<Boolean>?>(ResultAuth.Inactive)
    val signUpResult: StateFlow<ResultAuth<Boolean>?> = _signUpResult

    private val _logInResult = MutableStateFlow<ResultAuth<Boolean>?>(ResultAuth.Inactive)
    val logInResult: StateFlow<ResultAuth<Boolean>?> = _logInResult

    private val _resetPswdResult = MutableStateFlow<ResultAuth<Boolean>?>(ResultAuth.Inactive)
    val resetPswdResult: StateFlow<ResultAuth<Boolean>?> = _resetPswdResult

    /**
     * Creates a new user acount with a given email and password.
     * @param email The email of the user.
     * @param password The password of the user.
     */
    fun signUp(email: String, password: String) {
        _signUpResult.value = ResultAuth.InProgress

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val success = authRepository.signUp(email, password)
                _signUpResult.value = ResultAuth.Success(success)
            }
            catch(e: FirebaseAuthException) {
                _signUpResult.value = ResultAuth.Failure(e)
            }
//            finally {
//                resetResultValues()
//            }
        }
    }

    /**
     * Signs in a user if the given email and password are correct.
     * @param email The email of the user.
     * @param password The password of the user.
     */
    fun signIn(email: String, password: String) {
        _logInResult.value = ResultAuth.InProgress

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val success = authRepository.signIn(email, password)
                _logInResult.value = ResultAuth.Success(success)
            }
            catch(e: FirebaseAuthException) {
                _logInResult.value = ResultAuth.Failure(e)
            }
//            finally {
//                resetResultValues()
//            }
        }
    }

    /**
     * Signs out the current user.
     */
    fun signOut() {
        authRepository.signOut()
    }

    /**
     * Validates a given email and returns whether it's valid AND an error message if any.
     * @return Whether the email is valid AND an error message (could be null).
     */
    fun validateEmail(email: String): Boolean {
        return authRepository.isEmailValid(email)
    }

    /**
     * Validates a given password and returns whether it's valid AND an error message if any.
     * @return Whether the password is valid AND an error message (could be null).
     */
    fun validatePassword(password: String): Boolean {
        return authRepository.isPasswordValid(password)
    }

    /**
     * Deletes the current user.
     */
    fun delete() {
        viewModelScope.launch {
            authRepository.delete()
        }
    }

    /**
     * Sends an email to the current user's email address to reset their password.
     * @param email The email to send a password reset request to.
     */
    fun sendPasswordResetEmail(email: String) {
        _resetPswdResult.value = ResultAuth.InProgress

        viewModelScope.launch {
            try {
                if (authRepository.isEmailValid(email)) {
                    authRepository.sendPasswordResetEmail(email)
                    _resetPswdResult.value = ResultAuth.Success(true)
                }
                else
                    _resetPswdResult.value = ResultAuth.Success(false)
            }
            catch (e: Exception) {
                _resetPswdResult.value = ResultAuth.Failure(e)
            }
        }
    }

//    private fun resetResultValues() {
//        _signUpResult.value = ResultAuth.Inactive
//        _logInResult.value = ResultAuth.Inactive
//    }
}

