package kokoro.multiplatform.sasanqua.network.wrapper

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class JsonWrapper(
    val code: Int = 0,
    val msg: String = "",
    val data: JsonElement? = null,
){
    inline fun <reified T> getData(): T? {
        data?.let {
            return Json.decodeFromJsonElement(data)
        }
        return null
    }
    companion object{
        inline fun <reified T> success(message: String, data: T) = JsonWrapper(200, message, Json.encodeToJsonElement(data))
        inline fun <reified T> success(data: T) = JsonWrapper(200, "Success", Json.encodeToJsonElement(data))
        fun success() = JsonWrapper(200, "Success",null)
        fun success(message: String) = JsonWrapper(200, message,null)
    }
}