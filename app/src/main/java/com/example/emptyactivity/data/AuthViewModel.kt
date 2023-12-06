package com.example.emptyactivity.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository): ViewModel() {
    fun currentUser(): StateFlow<User?> {
        return authRepository.currentUser()
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            authRepository.signUp(email, password)
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

    fun validateEmail(email: String): Pair<Boolean, String?> {
        return authRepository.validateEmail(email)
    }

    fun validatePassword(password: String): Pair<Boolean, String?> {
        return authRepository.validatePassword(password)
    }

    fun delete() {
        viewModelScope.launch {
            authRepository.delete()
        }
    }
}

