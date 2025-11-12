package kokoro.mobile.hachimi.core

import android.os.Handler
import android.os.Looper
import kokoro.mobile.hachimi.BuildTypeConstant.BASE_URL
import kokoro.mobile.hachimi.model.NetLogModel
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.http.promisesBody
import okio.Buffer
import okio.GzipSource
import java.io.EOFException
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets.UTF_8
import java.util.concurrent.TimeUnit

/**
Created by Zebra-RD张先杰 on 2022年8月8日13:43:24

Description:
 */
class LogInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        NetLogModel().apply {
            val request = chain.request()
            val requestBody = request.body
            val connection = chain.connection()
            //--> PoST /greeting http/1.1
            requestStartMessage =
                ("${request.method} ${request.url.toString().removePrefix(BASE_URL)}${if (connection != null) " " + connection.protocol() else ""}")

            if (requestBody != null) {
                requestStartMessage += " (${requestBody.contentLength()}-byte body)"
            }

            val requestHeaders = request.headers
            if (requestBody != null) {
                // Request body headers are only present when installed as a network interceptor. When not
                // already present, force them to be included (if available) so their values are known.
                requestBody.contentType()?.let {
                    if (requestHeaders["Content-Type"] == null) {
                        this.requestHeaders.add("Content-Type: $it")
                    }
                }
                if (requestBody.contentLength() != -1L) {
                    if (requestHeaders["Content-Length"] == null) {
                        this.requestHeaders.add("Content-Length: ${requestBody.contentLength()}")
                    }
                }
            }

            for (i in 0 until requestHeaders.size) {
//                val value = if (headers.name(i) in headersToRedact) "██" else headers.value(i)
                this.requestHeaders.add(requestHeaders.name(i) + ": " + requestHeaders.value(i))
            }

            if (requestBody == null) {
                requestType = "END ${request.method}"
            } else if (bodyHasUnknownEncoding(request.headers)) {
                requestType = "END ${request.method} (encoded body omitted)"
            } else if (requestBody.isDuplex()) {
                requestType = "END ${request.method} (duplex request body omitted)"
            } else if (requestBody.isOneShot()) {
                requestType = "END ${request.method} (one-shot body omitted)"
            } else {
                val buffer = Buffer()
                requestBody.writeTo(buffer)

                val contentType = requestBody.contentType()
                val charset: Charset = contentType?.charset(UTF_8) ?: UTF_8
                //请求体
                if (buffer.isProbablyUtf8()) {
                    this.requestBody = buffer.readString(charset)
                    requestType =
                        "END ${request.method} (${requestBody.contentLength()}-byte body)"

                } else {
                    requestType =
                        "END ${request.method} (binary ${requestBody.contentLength()}-byte body omitted)"
                }
            }


            // 执行请求
            this.startTime = System.currentTimeMillis()

            val startNs = System.nanoTime()

            val response: Response
            try {
                response = chain.proceed(request)
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }

            val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

            val responseBody = response.body!!
            val contentLength = responseBody.contentLength()

            responseStartMessage =
                "${response.code}${if (response.message.isEmpty()) "" else ' ' + response.message} ${request.url.toString().removePrefix(
                    BASE_URL
                )} (${tookMs}ms)"

            val responseHeaders = response.headers
            for (i in 0 until responseHeaders.size) {
                this.responseHeaders.add(responseHeaders.name(i) + ": " + responseHeaders.value(i))
            }

            if (!response.promisesBody()) {
                responseType = "END HTTP"
            } else if (bodyHasUnknownEncoding(response.headers)) {
                responseType = "END HTTP (encoded body omitted)"
            } else {
                val source = responseBody.source()
                source.request(Long.MAX_VALUE) // Buffer the entire body.
                var buffer = source.buffer

                var gzippedLength: Long? = null
                if ("gzip".equals(responseHeaders["Content-Encoding"], ignoreCase = true)) {
                    gzippedLength = buffer.size
                    GzipSource(buffer.clone()).use { gzippedResponseBody ->
                        buffer = Buffer()
                        buffer.writeAll(gzippedResponseBody)
                    }
                }

                val contentType = responseBody.contentType()
                val charset: Charset = contentType?.charset(UTF_8) ?: UTF_8

                if (!buffer.isProbablyUtf8()) {
                    responseType = "END HTTP (binary ${buffer.size}-byte body omitted)"
                    return response
                }

                if (contentLength != 0L) {
                    this.responseBody = buffer.clone().readString(charset)
                }

                responseType = if (gzippedLength != null) {
                    "END HTTP (${buffer.size}-byte, $gzippedLength-gzipped-byte body)"
                } else {
                    "END HTTP (${buffer.size}-byte body)"
                }
            }
            Handler(Looper.getMainLooper()).post {
                NetLogDataManage.addLog(this)
            }
            return response
        }
    }

    private fun bodyHasUnknownEncoding(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"] ?: return false
        return !contentEncoding.equals("identity", ignoreCase = true) &&
                !contentEncoding.equals("gzip", ignoreCase = true)
    }

    private fun Buffer.isProbablyUtf8(): Boolean {
        try {
            val prefix = Buffer()
            val byteCount = size.coerceAtMost(64)
            copyTo(prefix, 0, byteCount)
            for (i in 0 until 16) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            return true
        } catch (_: EOFException) {
            return false // Truncated UTF-8 sequence.
        }
    }
}