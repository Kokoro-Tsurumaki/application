package kokoro.mobile.hachimi.core


import kokoro.mobile.hachimi.Variate
import okhttp3.Interceptor
import okhttp3.Response

/**
Created by Zebra-RD张先杰 on 2022年7月4日17:43:10

Description:请求头拦截器，添加请求头 全局默认请求头
 */
class BridgeInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //获取原始请求，在请求前修改
        val builder = chain.request().newBuilder()
        //提交的格式 默认为json
        builder.addHeader("Content-Type", "application/json; charset=utf-8")
        builder.addHeader("Accept-Encoding", "gzip,deflate,br")
        builder.addHeader("Accept", "*/*")
        builder.addHeader("Accept-Charset","utf-8")
        builder.addHeader("User-Agent","Android-Application")
        builder.addHeader("Platform","Android")
        builder.addHeader("Api-Token", Variate.token).build()
        return chain.proceed(builder.build())
    }
}