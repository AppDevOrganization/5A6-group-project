package com.example.emptyactivity.data

/**
 * Represents the status of an auth.
 */
sealed class ResultAuth<out T> {
    /**
     * Represents whether the auth's action was successful.
     */
    data class Success<out T>(val data: T): ResultAuth<T>()

    /**
     * An error that occurs in the auth's action.
     */
    data class Failure(val exception: Throwable): ResultAuth<Nothing>()

    /**
     * Represents the auth's state of doing nothing.
     */
    object Inactive: ResultAuth<Nothing>()

    /**
     * Represents the auth's state of doing something.
     */
    object InProgress: ResultAuth<Nothing>()
}