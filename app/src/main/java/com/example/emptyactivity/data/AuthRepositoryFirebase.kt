package com.example.emptyactivity.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import java.util.regex.Pattern

class AuthRepositoryFirebase(private val auth: FirebaseAuth): AuthRepository {
    private val MIN_PSWD_LENGTH = 8

    private val currentUserStateFlow = MutableStateFlow(auth.currentUser?.toUser())

    init {
        auth.addAuthStateListener { firebaseAuth ->
            currentUserStateFlow.value = firebaseAuth.currentUser?.toUser()
        }
    }

    /**
     * Returns a StateFlow of the current logged in user.
     * @return A StateFlow of the current logged in user.
     */
    override fun currentUser(): StateFlow<User?> {
        return currentUserStateFlow
    }

    /**
     * Creates a new user acount with a given email and password.
     * @param email The email of the user.
     * @param password The password of the user.
     * @return Whether the new user account was created or not.
     */
    override suspend fun signUp(email: String, password: String): Boolean {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            return true
        } catch(e: Exception) {
            return false
        }
    }

    /**
     * Signs in a user if the given email and password are correct.
     * @param email The email of the user.
     * @param password The password of the user.
     * @return Whether the sign-in attempt was successful.
     */
    override suspend fun signIn(email: String, password: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            return true
        } catch(e: Exception) {
            return false
        }
    }

    /**
     * Signs out the current user.
     */
    override fun signOut() {
        return auth.signOut()
    }

    /**
     * Validates an email and password and returns the result of whether they're valid (and an error message if not).
     * @param email The email to validate.
     * @param password The password to validate.
     * @return Whether the email and password are valid and an error message if not.
     */
    override fun validate(email: String, password: String): Pair<Boolean, String?> {
        if (!Pattern.matches("/^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$/g", email))
            return Pair(false, "Email is not in the correct format.")
        if (!Pattern.matches("/^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{${MIN_PSWD_LENGTH},}\$/gm", password))
            return Pair(false, "Password must contain at least ${MIN_PSWD_LENGTH} characters, 1 uppercase, 1 lowercase, and 1 number.")
        
        return Pair(true, null)
    }

    /**
     * Deletes the current user.
     */
    override suspend fun delete() {
        if (auth.currentUser != null) {
            auth.currentUser!!.delete()
        }
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