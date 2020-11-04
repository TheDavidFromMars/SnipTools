package com.jaqxues.sniptools.utils

import kotlinx.coroutines.channels.Channel


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.06.20 - Time 18:17.
 */
sealed class Request<out D> {
    object Loading : Request<Nothing>()
    class Success<out D>(val data: D): Request<D>()
    class Error(val t: Throwable): Request<Nothing>()
}
suspend infix fun <T> Channel<Request<T>>.sendAsRequest(action: suspend () -> T) {
    send(Request.Loading)
    try {
        send(Request.Success(action()))
    } catch (t: Throwable) {
        send(Request.Error(t))
    }
}