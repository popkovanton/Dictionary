package com.popkovanton.dictionary.data.model
import java.io.Serializable

data class Word (
    val id: Int,
    val meanings: ArrayList<Meaning>,
    val text: String
) : Serializable