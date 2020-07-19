package com.popkovanton.dictionary.utils

/**
 * A wrapper for database and network states.
 */
sealed class ResultWrapper<out T: Any>(val status: Status) {

    /**
     * A state of [data] which shows that we know there is still an update to come.
     */
    data class Loading<out T: Any>(val data: T?) : ResultWrapper<T>(status = Status.LOADING)

    /**
     * A state that shows the [data] is the last known update.
     */
    data class Success<out T: Any>(val data: T) : ResultWrapper<T>(status = Status.SUCCESS)

    /**
     * A state to show a [throwable] is thrown beside the [lastData] which is cached.
     */
    data class Error(val exception: Exception) : ResultWrapper<Nothing>(status = Status.ERROR)

}