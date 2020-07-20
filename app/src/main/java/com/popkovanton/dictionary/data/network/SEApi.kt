package com.popkovanton.dictionary.data.network

import com.popkovanton.dictionary.data.model.MeaningDetails
import com.popkovanton.dictionary.data.model.Word
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SEApi {
    @GET("words/search")
    fun searchWordAsync(@Query("search") search: String = "") : Deferred<Response<List<Word>>>

    @GET("meanings")
    fun wordMeaningsAsync(@Query("ids") ids: String): Deferred<Response<List<MeaningDetails>>>
}