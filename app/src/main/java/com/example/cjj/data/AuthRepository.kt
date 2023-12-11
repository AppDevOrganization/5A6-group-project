package com.example.emptyactivity.data

import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    fun currentUser(): StateFlow<User?>
    suspend fun signUp(email: String, password: String): Boolean
    suspend fun signIn(email: String, password: String): Boolean
    fun signOut()
    fun isEmailValid(email: String): Boolean
    fun isPasswordValid(password: String): Boolean
    suspend fun delete()
}