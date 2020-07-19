package com.popkovanton.dictionary.data.network

import Word
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SEApi {
    @GET("words/search")
    fun searchWordAsync(@Query("search") search: String = "") : Deferred<Response<List<Word>>>
}