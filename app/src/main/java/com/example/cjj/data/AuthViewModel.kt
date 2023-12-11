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
            finally {
                resetResultValues()
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            authRepository.signIn(email, password)
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

    private fun resetResultValues() {
        // TODO: Implement
    }
}

