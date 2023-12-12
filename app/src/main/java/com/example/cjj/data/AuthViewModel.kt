package com.example.emptyactivity.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository): ViewModel() {
    fun currentUser(): StateFlow<User?> {
        return authRepository.currentUser()
    }

    private val _signUpResult = MutableStateFlow<ResultAuth<Boolean>?>(ResultAuth.Inactive)
    val signUpResult: StateFlow<ResultAuth<Boolean>?> = _signUpResult

    private val _logInResult = MutableStateFlow<ResultAuth<Boolean>?>(ResultAuth.Inactive)
    val logInResult: StateFlow<ResultAuth<Boolean>?> = _logInResult

    private val _resetPswdResult = MutableStateFlow<ResultAuth<Boolean>?>(ResultAuth.Inactive)
    val resetPswdResult: StateFlow<ResultAuth<Boolean>?> = _resetPswdResult

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

    fun signOut() {
        authRepository.signOut()
    }

    fun validateEmail(email: String): Boolean {
        return authRepository.isEmailValid(email)
    }

    fun validatePassword(password: String): Boolean {
        return authRepository.isPasswordValid(password)
    }

    fun delete() {
        viewModelScope.launch {
            authRepository.delete()
        }
    }

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

