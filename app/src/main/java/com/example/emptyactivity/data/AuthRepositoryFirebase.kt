package com.example.emptyactivity.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class AuthRepositoryFirebase(private val auth: FirebaseAuth): AuthRepository {
    private val currentUserStateFlow = MutableStateFlow(auth.currentUser?.toUser())

    override suspend fun currentUser(): StateFlow<User> {
        TODO("Not yet implemented")
    }

    override suspend fun signUp(email: String, password: String): Boolean {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            return true
        } catch(e: Exception) {
            return false
        }
    }

    override fun signIn(email: String, password: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }

    override suspend fun delete() {
        TODO("Not yet implemented")
    }

    // Convert from FirebaseUser to User
    private fun FirebaseUser?.toUser(): User? {
        return this?.let {
            if (it.email == null)
                null
            else
                User(
                    email = it.email!!,
                )
        }
    }
}