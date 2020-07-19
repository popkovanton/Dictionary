package com.popkovanton.dictionary.data.repository

import android.util.Log
import com.popkovanton.dictionary.utils.ResultWrapper
import retrofit2.Response
import java.io.IOException

open class BaseRepository {
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): ResultWrapper<T> {
        val result: ResultWrapper<T> = safeApiResult(call)
        if (result is ResultWrapper.Error) {
            Log.d("WordRepository", "Exception - ${result.exception}")
        }
        return result
    }

    suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>): ResultWrapper<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) return ResultWrapper.Success(response.body()!!)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        return ResultWrapper.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR"))
    }
}