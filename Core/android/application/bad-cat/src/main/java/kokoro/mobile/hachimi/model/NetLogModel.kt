package kokoro.mobile.hachimi.model

import okhttp3.Headers
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.internal.http.promisesBody
import okio.Buffer
import okio.GzipSource
import java.io.EOFException
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import kotlin.text.Charsets.UTF_8

/**
 * Created by xianjie on  2025年1月14日10:32:20
 *
 * Description:
 */
data class NetLogModel(
    //代替id
    var startTime: Long = -1,



    //requestInfo
    var requestStartMessage:String = "",
    //请求头
    var requestHeaders: ArrayList<String> = arrayListOf(),
    //请求体
    var requestBody:String = "",
    //请求结束Type
    var requestType:String = "",


    //responseInfo
    var responseStartMessage:String = "",
    //响应头
    var responseHeaders: ArrayList<String> = arrayListOf(),
    //响应体
    var responseBody: String = "",
    //响应结束Type
    var responseType:String = ""
    )