package com.example.emptyactivity.data

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emptyactivity.data.AuthRepository
import com.example.emptyactivity.data.User
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

    fun delete() {
        viewModelScope.launch {
            authRepository.delete()
        }
    }
}
