package com.popkovanton.dictionary.data.model

import java.io.Serializable

data class MeaningDetails(
    val id: Int,
    val text: String,
    val transcription: String,
    val translation: Translation,
    val definition: Definition,
    val images: ArrayList<Images>
) : Serializable

data class Images(
    val url: String?
) : Serializable

data class Definition(
    val text: String?,
    val soundUrl: String?
) : Serializable

data class Translation(
    val note: String?,
    val text: String?
) : Serializable