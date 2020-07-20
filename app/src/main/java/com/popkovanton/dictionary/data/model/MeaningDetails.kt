package com.popkovanton.dictionary.data.model

import java.io.Serializable

data class MeaningDetails(
    val id: Int,
    val text: String,
    val transcription: String,
    val translation: Translation,
    val definition: Definition
) : Serializable

data class Definition(
    val text: String?,
    val soundUrl: String?
) : Serializable