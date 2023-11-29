package com.example.emptyactivity.data

interface AuthRepository {
    fun currentUser()
    fun signUp()
    fun signIn()
    fun signOut()
    fun delete()
}