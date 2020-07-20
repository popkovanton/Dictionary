package com.popkovanton.dictionary.data.model

import java.io.Serializable

data class Translation(
    val note: String?,
    val text: String?
) : Serializable