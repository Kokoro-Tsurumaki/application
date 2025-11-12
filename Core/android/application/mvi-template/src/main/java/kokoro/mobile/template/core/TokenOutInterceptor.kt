package kokoro.mobile.template.core

import kokoro.mobile.template.ui.AppEvent
import kokoro.mobile.template.ui.EventBus
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response

/**
Created by Zebra-RD张先杰 on 2022年7月13日11:49:27

Description:授权信息失效拦截器
 */
class TokenOutInterceptor : Interceptor {
    val gson: Gson by lazy { Gson() }
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
         if (response.body != null && response.body!!.contentType() != null) {
            if (response.code == 900) {
//                refreshToken()
//                tokenInvalidDispose()
                GlobalScope.launch {
                    _root_ide_package_.kokoro.mobile.template.ui.EventBus.send(_root_ide_package_.kokoro.mobile.template.ui.AppEvent.ResetApp)
                }
            }else if (response.code == 1000){

            }
        }
        return response
    }

}