package kokoro.mobile.network.handle

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import kokoro.mobile.network.Constant
import kokoro.mobile.network.Network
import kokoro.mobile.network.handle.pack.JsonWrapper
import kokoro.mobile.network.handle.pack.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception

/**
Created by Zebra-RD张先杰 on 2022年7月12日10:22:01

Description:对请求和响应最主要的处理
 */


/**
 * 创建一个冷流进行请求，返回它
 */

fun Flow<Result>.launchWithCallback(
    viewModelScope: CoroutineScope,
    callback: Result.FileResultCallback<String>){
    launch(viewModelScope,{},{callback.onFailure(it)},{ response->
        Network.setProgressCallback {bytesRead,contentLength,percent->
            callback.onProgress(bytesRead,contentLength,percent)
        }
        callback.onSuccess(response.body()!!)
    })
}
inline fun <reified T : Any> Flow<Result>.launchWithCallback(
    viewModelScope: CoroutineScope,
    callback: Result.JsonWrapperResultCallback<T>){
    launch(viewModelScope,{callback.onLoading()},{callback.onFailure(it)},{ response->
        response.parseJson<JsonWrapper<T>>(onFailure = {callback.onFailure(it)}){ jsonModel->
            Log.e("TAG", "launchWithCallback: "+jsonModel )
            if (jsonModel.code == 200){
                callback.onSuccess(jsonModel.msg,jsonModel.data)
            }else{
                callback.onFailure(Exception(jsonModel.msg))
            }
        }
    })
}


/**
 * 对脱壳或未脱壳的数据json解析
 * 注意不要嵌套传入泛型，泛型擦除会导致无法获取嵌套中的具体类型
 * 对于数组使用下方的重写方法处理吧
 * ```
 * safeParse(
 *      result,
 *      Model::class.java,
 *      error ?: {},
 *      { msg, data -> success.invoke(data) }
 *    )
 * ```
 * @param onFailure 异常时的回调
 * @param onSuccess 成功时的回调
 */
inline fun <reified T : Any> Response<ResponseBody>.parseJson(
    onFailure: ((Throwable) -> Unit),
    onSuccess: ((T) -> Unit)
) {
    val result = body()?.string()
    Log.e(Constant.TAG, "parseJson: "+this.errorBody()?.string(), )
    val dataDetail = runCatching {
        Gson().fromJson<T>(result, object : TypeToken<T>() {}.type)
    }.onFailure {
        Log.e(Constant.TAG, it.message, it)
    }.getOrNull()
    if (dataDetail != null) {
        onSuccess.invoke(dataDetail)
    } else {
        val exception = JsonParseException("parseJson data be blank")
        Log.e(Constant.TAG, exception.message, exception)
        onFailure.invoke(exception)
    }
}

fun Map<String,Any>.toJson(): RequestBody{
    return Gson().toJson(this).toRequestBody(
        "application/json;charset=utf-8"
            .toMediaType()
    )
}



fun Flow<Result>.launch(
    viewModelScope: CoroutineScope,
    onLoading: (() -> Unit)?,
    onFailure: ((Throwable) -> Unit)?,
    onSuccess: ((Response<ResponseBody>) -> Unit)
) {
    onEach { result ->
        when (result.status) {
            Result.ResultStatus.Loading -> {
                onLoading?.invoke()
            }

            Result.ResultStatus.Success -> {
                if (result.response != null) {
                    onSuccess(result.response!!)
                } else {
                    Log.e(Constant.TAG, "Response<ResponseBody> be null")
                }
            }

            Result.ResultStatus.Failure -> {
                onFailure?.invoke(result.exception)
            }

            Result.ResultStatus.Default -> {
                Log.e(Constant.TAG, "Launch-Default")
            }
        }
    }
        .launchIn(viewModelScope)
}


//这一步是有真正执行请求的方法
fun fetch(
    request: suspend () -> Response<ResponseBody>,
): Flow<Result> = flow {
    emit(Result(Result.ResultStatus.Loading))
    //进行网络请求
    runCatching {
        request()
    }.onFailure {
        Log.e(Constant.TAG, it.message, it)
        emit(Result(Result.ResultStatus.Failure, exception = it))
    }.onSuccess {
        emit(Result(Result.ResultStatus.Success, response = it))
    }
}