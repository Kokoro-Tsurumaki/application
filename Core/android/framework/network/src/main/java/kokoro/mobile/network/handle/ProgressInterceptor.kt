package kokoro.mobile.network.handle

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import okio.ForwardingSource
import okio.buffer

class ProgressInterceptor(
    private  val onProgress: (bytesRead: Long, contentLength: Long) -> Unit
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        val contentLength = originalResponse.body?.contentLength() ?: -1L
         originalResponse.newBuilder()
            .body(originalResponse.body?.let { body ->
                ProgressResponseBody(body, contentLength, onProgress)
            })
            .build()
        return originalResponse

    }
}


class ProgressResponseBody(
    private val originalBody: ResponseBody,
    private val contentLength: Long,
    private val onProgress: (bytesRead: Long, contentLength: Long) -> Unit
) : ResponseBody() {
    private var bytesRead = 0L

    override fun contentType(): MediaType? = originalBody.contentType()
    override fun contentLength(): Long = contentLength

    override fun source(): BufferedSource {
        return object : ForwardingSource(originalBody.source()) {
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesCopied = super.read(sink, byteCount)
                // 更新已读取字节数（-1表示流结束）
                if (bytesCopied != -1L) {
                    bytesRead += bytesCopied
                }
                // 触发回调（包括流结束时的最终状态）
                onProgress(bytesRead, contentLength)
                return bytesCopied
            }
        }.buffer()
    }
}