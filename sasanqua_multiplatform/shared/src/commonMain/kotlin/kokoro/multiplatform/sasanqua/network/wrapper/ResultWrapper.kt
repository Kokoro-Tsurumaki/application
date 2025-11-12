package kokoro.multiplatform.sasanqua.network.wrapper

import io.ktor.client.statement.*

/**
Created by Zebra-RD张先杰 on 2022年7月5日15:05:12

Description:对返回数据进行的包装，主要用于区分请求与响应的状态
 */
class ResultWrapper(
    //响应状态 -Loading -Success 等
    var status: ResultStatus = ResultStatus.Default,
    //当响应为文字时 的response body
    var response: HttpResponse? = null,
    //当请求失败时 的错误
    var exception: Throwable = IllegalArgumentException("Default Throwable: Did you forget to set the exception?"),
    ) {
    sealed class ResultStatus{
        object Loading: ResultStatus()
        object Success: ResultStatus()
        object Failure: ResultStatus()
        object Default: ResultStatus()
    }
     interface Callback<T>{
         fun onFailure(t:Throwable)
     }

    interface JsonWrapperResultCallback<T>: Callback<T> {
        fun onLoading()
        fun onSuccess(code:Int,msg:String, data: T?)
    }

    interface StringResultCallback {
        fun onLoading()
        fun onSuccess(data: String)
        fun onFailure(t:Throwable)
    }

    interface FileResultCallback<T> : Callback<T> {
        fun onProgress(progress: Long, total: Long,percent: Int)
        fun onSuccess(body:JsonWrapper)
    }
}