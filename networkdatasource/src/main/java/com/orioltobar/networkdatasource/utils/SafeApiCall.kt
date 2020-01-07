package com.orioltobar.networkdatasource.utils

import com.orioltobar.commons.CoroutineResponse
import com.orioltobar.commons.Failure
import com.orioltobar.commons.Success

/**
 * This function is used to wrap a [call] to an API in a safe way. The result is expressed as a
 * Coroutine Response which is a sealed class that contains the result of the operation.
 */
suspend fun <T> safeApiCall(
    call: suspend () -> T
): CoroutineResponse<T> =
    try {
        val response = call()
        if (response == null) {
            Failure(null)
        } else {
            Success(response)
        }
    } catch (exception: Exception) {
        Failure(exception)
    }