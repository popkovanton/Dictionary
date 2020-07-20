package com.popkovanton.dictionary.data.repository

import com.popkovanton.dictionary.data.model.MeaningDetails
import com.popkovanton.dictionary.data.model.Word
import com.popkovanton.dictionary.data.network.SEApi
import com.popkovanton.dictionary.utils.ResultWrapper

class WordRepository(private val SEApi: SEApi) : BaseRepository() {
    suspend fun searchWords(search: String = ""): ResultWrapper<List<Word>> {
        return safeApiCall(call = { SEApi.searchWordAsync(search).await() })
    }

    suspend fun searchMeaning(search: String = ""): ResultWrapper<List<MeaningDetails>> {
        return safeApiCall(call = { SEApi.wordMeaningsAsync(search).await() })
    }
}