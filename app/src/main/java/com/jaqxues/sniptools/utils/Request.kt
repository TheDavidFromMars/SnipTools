package com.jaqxues.sniptools.utils


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.06.20 - Time 18:17.
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

sealed class Request<out T> {
    object Pending : Request<Nothing>()
    class Loaded<out T>(val result: Result<T>) : Request<T>()

    val success: Boolean
        get() = this@Request is Loaded && result is Result.Success
}