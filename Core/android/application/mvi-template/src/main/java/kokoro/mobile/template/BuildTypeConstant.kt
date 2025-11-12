package kokoro.mobile.template

import kokoro.mobile.template.core.BridgeInterceptor
import kokoro.mobile.template.core.LogInterceptor
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by xianjie on 2023年1月3日17:33:43
 *
 * Description:
 */
object BuildTypeConstant {
    //    const val h5BaseUrl = "https://testmars.pcoinsight.com/mobileH5#/"
//    const val BASE_URL = "https://kokoro.xj.cn/spring-api/"
    const val BASE_URL = "http://192.168.31.198:8080/"

    val interceptorList = arrayListOf(
        HttpLoggingInterceptor().apply {
            level =
                HttpLoggingInterceptor.Level.BODY
        },
        _root_ide_package_.kokoro.mobile.template.core.LogInterceptor(),
        _root_ide_package_.kokoro.mobile.template.core.BridgeInterceptor()
    )
}