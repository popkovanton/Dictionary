package com.popkovanton.dictionary.data.repository

import Word
import com.popkovanton.dictionary.data.network.SEApi
import com.popkovanton.dictionary.utils.ResultWrapper

class WordRepository(private val SEApi: SEApi) : BaseRepository() {
    suspend fun searchWords(search: String = ""): ResultWrapper<List<Word>> {
        return safeApiCall(call = { SEApi.searchWordAsync(search).await() })
    }
}