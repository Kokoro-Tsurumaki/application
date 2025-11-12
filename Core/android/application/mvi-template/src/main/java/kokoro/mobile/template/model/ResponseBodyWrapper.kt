package kokoro.mobile.template.model

data class ResponseBodyWrapper<T>(
    val code: Int = 0,
    val data: T,
    val msg: String = "",
)

