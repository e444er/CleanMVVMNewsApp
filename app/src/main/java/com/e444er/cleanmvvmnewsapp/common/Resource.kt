package com.e444er.cleanmvvmnewsapp.common

sealed class Resource<T>(
    val data : T? = null,
    val errorMessage : String? = null
) {
    class Success<T>(data: T) : Resource<T>(data = data)

    class Error<T>(data: T?= null, errorMessage: String) : Resource<T>( data, errorMessage)

    class Loading<T>(data: T?= null) : Resource<T>(data = data)
}
