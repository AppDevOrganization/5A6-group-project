package com.example.emptyactivity.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthRepositoryFirebase(private val auth: FirebaseAuth): AuthRepository {
    private val currentUserStateFlow = MutableStateFlow(auth.currentUser?.toUser())

    override fun currentUser(): StateFlow<User> {
        TODO("Not yet implemented")
    }

    override fun signUp(email: String, password: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun signIn(email: String, password: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }

    override fun delete() {
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