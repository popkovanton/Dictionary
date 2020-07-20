package com.popkovanton.dictionary.extentions

import com.popkovanton.dictionary.data.model.DetailsFinal
import com.popkovanton.dictionary.data.model.MeaningDetails
import com.popkovanton.dictionary.data.model.Word

fun MeaningDetails.toDetailsFinal(word: Word): DetailsFinal {
    return DetailsFinal(
        text = text,
        translation = translation.text,
        transcription = "/${transcription}/",
        definition = definition.text,
        imageUrl = "https:${word.meanings.getOrNull(0)?.imageUrl}"
    )
}