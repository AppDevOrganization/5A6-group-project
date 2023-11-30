package com.example.emptyactivity.data

import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    fun currentUser(): StateFlow<User>
    fun signUp(email: String, password: String): Boolean
    fun signIn(email: String, password: String): Boolean
    fun signOut()
    fun delete()
}