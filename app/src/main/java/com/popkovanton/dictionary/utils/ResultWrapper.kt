package com.popkovanton.dictionary.utils

sealed class ResultWrapper<out T: Any>(val status: Status) {

    data class Loading<out T: Any>(val data: T?) : ResultWrapper<T>(status = Status.LOADING)

    data class Success<out T: Any>(val data: T) : ResultWrapper<T>(status = Status.SUCCESS)

    data class Error(val exception: Exception) : ResultWrapper<Nothing>(status = Status.ERROR)

}