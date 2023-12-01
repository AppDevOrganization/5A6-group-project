package com.example.emptyactivity

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.emptyactivity.data.AuthRepository
import com.example.emptyactivity.data.User
import kotlinx.coroutines.flow.StateFlow

@Composable
fun AuthViewModel(
    authRepository: AuthRepository,
    modifier: Modifier = Modifier
) {
    fun currentUser(): StateFlow<User?> {
        return authRepository.currentUser()
    }
}

