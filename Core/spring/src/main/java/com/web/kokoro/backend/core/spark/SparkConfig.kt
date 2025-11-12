package com.web.kokoro.backend.core.spark

import com.alibaba.fastjson.JSON
import com.mobile.hotel.model.DeepseekRequestModel
import kokoro.mobile.SparkRequestModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration

class SparkConfig {
    @Value("\${spark.api-key}")
    lateinit var apiKey: String

    fun getRequestBody(content: String):RequestBody{
        val requestParams = SparkRequestModel(
            model = "generalv3",
            user = "user_123456",
            messages = arrayListOf(SparkRequestModel.Message("标准md格式","system"),SparkRequestModel.Message(content,"user"))
        )
        return JSON.toJSONString(requestParams).toRequestBody("application/json;charset=utf-8".toMediaType())
    }
}