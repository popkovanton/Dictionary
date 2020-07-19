
import java.io.Serializable

data class Meaning(
    val id: Int,
    val imageUrl: String,
    val partOfSpeechCode: String,
    val previewUrl: String,
    val soundUrl: String,
    val transcription: String,
    val translation: Translation
) : Serializable {

    companion object PartOfSpeech {
        const val NOUN = "n"
        const val VERB = "v"
        const val ADJECTIVE = "j"
        const val ADVERB = "r"
        const val PREPOSITION = "prp"
        const val PRONOUN = "prn"
        const val CARDINAL_NUMBER = "crd"
        const val CONJUNCTION = "cjc"
        const val INTERJECTION = "exc"
        const val ARTICLE = "det"
        const val ABBREVIATION = "abb"
        const val PARTICLE = "x"
        const val ORDINAL_NUMBER = "ord"
        const val MODAL_VERB = "md"
        const val PHRASE = "ph"
        const val IDIOM = "phi"
    }
}