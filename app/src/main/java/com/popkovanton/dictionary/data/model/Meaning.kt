package com.popkovanton.dictionary.data.model
import java.io.Serializable

data class Meaning(
    val id: Int,
    val imageUrl: String,
    val translation: Translation
) : Serializable