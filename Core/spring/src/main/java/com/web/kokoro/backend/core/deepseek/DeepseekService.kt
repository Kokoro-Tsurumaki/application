package com.web.kokoro.backend.core.deepseek

import com.alibaba.fastjson.JSON
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.mobile.hotel.model.CompletionsRequest
import com.mobile.hotel.model.DeepseekRequestModel
import com.mobile.hotel.model.DeepseekResponseModel
import com.web.kokoro.backend.base.Result
import com.web.kokoro.backend.core.user.RegisterRequest
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.springframework.boot.json.GsonJsonParser
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.io.IOException
import java.util.*


@Service
class DeepseekService(private val deepseekConfig: DeepseekConfig,private val objectMapper:ObjectMapper) {

    /**
     * 注册
     */
    fun requestChat(requestBody: CompletionsRequest,readCallback:((String)->Unit),finishCallback:()->Unit) {
        val client: OkHttpClient = OkHttpClient().newBuilder()
            .build()

        val request: Request = Request.Builder()
            .url("https://api.deepseek.com/chat/completions")
            .method("POST", deepseekConfig.getRequestBody(requestBody.content,requestBody.isReasoner))
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", "Bearer ${deepseekConfig.apiKey}")
            .build()
        println("开始请求")
        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                response.body!!.source().use { source ->
                    while (!source.exhausted()) {
                        val line = source.readUtf8Line() ?: break // 逐行读取
                        println(line)
                        if (line.startsWith("data:")) {
                            val trimLine = line.substring(5).trim { it <= ' ' }
                            try {
                                 val jsonModel = objectMapper.readValue<DeepseekResponseModel>(trimLine, DeepseekResponseModel::class.java)
                                if (jsonModel.choices.isNullOrEmpty()){
                                    continue
                                }
                                if (jsonModel.choices[0] == null){
                                    continue
                                }

                                if (jsonModel.choices[0]!!.delta == null){
                                    continue
                                }
                                if (jsonModel.choices[0]?.delta?.content!!.isBlank()){
                                    continue
                                }
                                readCallback(jsonModel.choices[0]?.delta?.content?:"")
                            }catch (e: MismatchedInputException) {
                                finishCallback.invoke()
                                continue
                            }
//                            readCallback(trimLine)
                        }
                    }
                }
            }

        })
    }



}