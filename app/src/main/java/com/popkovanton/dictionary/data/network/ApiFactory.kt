package com.popkovanton.dictionary.data.network

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.popkovanton.dictionary.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiFactory {
    private const val TIMEOUT_CONNECT = 2L
    private const val TIMEOUT_READ = 2L
    private const val TIMEOUT_WRITE = 2L

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient().newBuilder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
        .build()

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    fun getClient(): SEApi {
        return retrofit.create(SEApi::class.java)
    }
}