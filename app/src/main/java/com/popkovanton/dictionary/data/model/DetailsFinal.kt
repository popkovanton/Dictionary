package com.popkovanton.dictionary.data.model

data class DetailsFinal(
    val text: String,
    val translation: String?,
    val transcription: String,
    val definition: String?,
    val imageUrl: String
)