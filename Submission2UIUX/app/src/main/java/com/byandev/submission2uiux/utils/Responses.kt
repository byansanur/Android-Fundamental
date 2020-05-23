package com.byandev.submission2uiux.utils

sealed class Responses<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Responses<T>(data)
    class Error<T>(message: String, data: T? = null) : Responses<T>(data, message)
    class Loading<T> : Responses<T>()
}