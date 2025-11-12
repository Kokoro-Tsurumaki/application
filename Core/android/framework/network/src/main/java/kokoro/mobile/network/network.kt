package kokoro.mobile.network

import android.app.Application
import android.content.Context
import kokoro.mobile.network.handle.ProgressInterceptor
import okhttp3.Cache
import okhttp3.CookieJar
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by Zebra-RD张先杰 on 2022年6月30日10:03:35
 *
 * Description:网络管理与配置
 */

/**
 * 创建Retrofit实例与ApiService实例
 */
fun Application.network(context: Context, onConfig: (Network.() -> Unit)) {
    val network = Network.create(context)
    onConfig(network)
}


class Network private constructor() {
    companion object {
        private val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { Network() }


        @Synchronized
        fun create(context: Context): Network {
            instance.context = context
            return instance
        }

        fun get():Network = instance

        fun <T>getApi(clazz:Class<T>) =
            instance.createApi(clazz)

        fun setProgressCallback(onProgress:(bytesRead: Long, contentLength: Long,percent: Int)->Unit){
            instance.onProgress = onProgress
        }

    }

    lateinit var context: Context

    private lateinit var config: Config


    private lateinit var retrofit: Retrofit

    fun  getConfig() = config


    fun config(onConfig: Config.() -> Unit) {
        config = Config(context)
        onConfig.invoke(config)
        config.build()
    }

    private fun Config.build() {
        require(baseUrl.isNotBlank()) { "baseUrl must not be null" }
        //创建OkHttpClient并配置
        if (builder == null) builder = OkHttpClient.Builder()
            .readTimeout(readTimeOut, TimeUnit.SECONDS)
            .writeTimeout(writeTimeOut, TimeUnit.SECONDS)
            .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
            .cache(Cache(cacheFile, cacheSize))//设置缓存配置 缓存最大10M
        builder!!.let {builder->
            //设置CookieJar
            if (cookieJar != null)
                builder.cookieJar(cookieJar!!)
            //配置拦截器
            interceptors.forEach {
                builder.addInterceptor(it)
            }
            builder.addInterceptor(ProgressInterceptor { bytesRead, contentLength ->
                onProgress?.invoke(bytesRead, contentLength,if (contentLength > 0) (bytesRead * 100 / contentLength).toInt() else -1)
            })
            val retrofitBuilder = Retrofit.Builder()
                .client(builder.build())
                .baseUrl(baseUrl)
            //设置格式转换器
            converters.forEach {
                retrofitBuilder.addConverterFactory(it)
            }
            retrofit = retrofitBuilder.build()
        }
    }

    var onProgress:((bytesRead: Long, contentLength: Long, percent: Int)->Unit)? = null
     fun <T>createApi(clazz:Class<T>): T =
          retrofit.create(clazz)

}

/**
 * Retrofit/Okhttp 相关配置
 */
class Config constructor(
    private val context: Context,
) {

    var builder: OkHttpClient.Builder? = null

    var baseUrl = ""

    //缓存文件路径
    val cacheFile by lazy { File(context.externalCacheDir, "NetworkCache") }

    //缓存文件大小
    var cacheSize = 10 * 1024 * 1024L

    //设置连接的超时时间
    var connectTimeOut = 10L

    //设置读的超时时间
    var readTimeOut = 5L

    //设置写的超时时间
    var writeTimeOut = 5L

    //加入创建后的转换器，如:GsonConverterFactory.create(GsonBuilder().create())
    var converters = mutableListOf<Converter.Factory>()

    //Okhttp拦截器
    var interceptors = mutableListOf<Interceptor>()

    //Cookie持久化
    var cookieJar: CookieJar? = null
}