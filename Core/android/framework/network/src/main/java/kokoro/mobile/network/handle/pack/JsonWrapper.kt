package kokoro.mobile.network.handle.pack

data class JsonWrapper<T>(
    val code: Int = 0,
    val data: T,
    val msg: String = "",
)