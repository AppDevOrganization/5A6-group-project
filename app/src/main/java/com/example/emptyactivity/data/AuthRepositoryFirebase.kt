package com.example.emptyactivity.data

import kotlinx.coroutines.flow.StateFlow

class AuthRepositoryFirebase: AuthRepository {
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

}