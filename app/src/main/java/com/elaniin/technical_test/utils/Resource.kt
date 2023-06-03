package com.elaniin.technical_test.utils

sealed class Resource<out T> {

    data class Success<T>(val data: T) : Resource<T>()

    data class Error(val errorMessage: String) : Resource<Nothing>()

    data class Loading<T>(val data: T? = null) : Resource<T>()

}