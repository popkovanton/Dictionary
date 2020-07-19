package com.popkovanton.dictionary

import com.popkovanton.dictionary.data.network.ApiFactory
import com.popkovanton.dictionary.data.repository.WordRepository
import com.popkovanton.dictionary.utils.ResultWrapper
import kotlinx.coroutines.runBlocking
import org.junit.Test

class NetworkUnitTest {

    @Test
    fun testNetworkConnection() {
        runBlocking {
            val response = ApiFactory.getClient().searchWordAsync("book").await()
            assert(response.isSuccessful)
        }
    }

    @Test
    fun testWordRepository() {
        val repository = WordRepository(ApiFactory.getClient())
        runBlocking {
            val result = repository.searchWords("book")
            assert(result is ResultWrapper.Success)
        }
    }
}