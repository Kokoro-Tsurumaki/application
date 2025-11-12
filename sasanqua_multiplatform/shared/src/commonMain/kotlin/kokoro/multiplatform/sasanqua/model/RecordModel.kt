package kokoro.multiplatform.sasanqua.model

import kotlinx.serialization.Serializable

@Serializable
data class TypeModel(val id: Int? = null, val type: String)

@Serializable
open class RecordModel(
    val id: Int? = null,
    val types: String,      //1,2,3
    val title: String = "",
    val hint: String = "",
    val fileUrl: String = "",
    val createTime: String = "",
    val updateTime: String? = null
)