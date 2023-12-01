package com.example.emptyactivity.data

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AppModule(
    private val appContext: Context
) {
    /* Create appropriate repository (backed by a DataStore) on first use.
    Only one copy will be created during lifetime of the application. */
    val userPreferencesRepository : UserPreferencesRepository by lazy {
        UserPreferencesRepository(context = appContext)
    }
    val authRepository : AuthRepository by lazy {
        AuthRepositoryFirebase(Firebase.auth)
    }
}