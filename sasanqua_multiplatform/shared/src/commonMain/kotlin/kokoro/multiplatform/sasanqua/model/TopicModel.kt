package kokoro.multiplatform.sasanqua.model

import kotlinx.serialization.Serializable


@Serializable
open class TopicModel(
    val id: Int? = null,
    val type: String,      //1,2,3
    val title: String,
    val answer: String,
)