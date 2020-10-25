package com.jaqxues.sniptools.utils


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.06.20 - Time 18:17.
 */
sealed class Request<out T> {
    object Pending : Request<Nothing>()
    data class Success<out T>(val data: T) : Request<T>()
    data class Error(val throwable: Throwable) : Request<Nothing>()
}