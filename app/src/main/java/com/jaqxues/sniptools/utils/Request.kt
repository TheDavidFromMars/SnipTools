package com.jaqxues.sniptools.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import kotlinx.coroutines.channels.Channel
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.06.20 - Time 18:17.
 */
sealed class Request<out D> {
    object Loading : Request<Nothing>()
    class Success<out D>(val data: D) : Request<D>()
    class Error(val t: Throwable) : Request<Nothing>()
}

suspend infix fun <T> Channel<Request<T>>.sendAsRequest(action: suspend () -> T) {
    send(Request.Loading)
    try {
        send(Request.Success(action()))
    } catch (t: Throwable) {
        send(Request.Error(t))
    }
}

fun <T> MutableLiveData<Request<T>>.consumeRequest(
    lifecycleOwner: LifecycleOwner,
    observer: (Request<T>) -> Unit
) {
    observe(lifecycleOwner, object : Observer<Request<T>> {
        override fun onChanged(t: Request<T>?) {
            t ?: return
            observer(t)
            if (t !is Request.Loading)
                removeObserver(this)
        }
    })
}

fun <T> liveDataRequest(
    context: CoroutineContext = EmptyCoroutineContext,
    action: suspend () -> T
) = liveData(context) {
    emit(Request.Loading)
    emit(
        try {
            Request.Success(action())
        } catch (t: Throwable) {
            Request.Error(t)
        }
    )
}