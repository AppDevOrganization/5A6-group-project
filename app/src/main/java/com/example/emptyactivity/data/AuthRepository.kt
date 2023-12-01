package com.example.emptyactivity.data

import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    suspend fun currentUser(): StateFlow<User>
    suspend fun signUp(email: String, password: String): Boolean
    fun signIn(email: String, password: String): Boolean
    fun signOut()
    suspend fun delete()
}