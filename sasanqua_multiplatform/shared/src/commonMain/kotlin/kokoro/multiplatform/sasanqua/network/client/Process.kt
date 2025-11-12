package kokoro.multiplatform.sasanqua.network.client

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.JsonConvertException
import kokoro.multiplatform.sasanqua.network.wrapper.JsonWrapper
import kokoro.multiplatform.sasanqua.network.wrapper.ResultWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.json.Json

fun   fetch(request: suspend () -> HttpResponse): Flow<ResultWrapper> = flow {
    emit(ResultWrapper(ResultWrapper.ResultStatus.Loading))
    //进行网络请求
    runCatching {
        request()
    }.onFailure {
        println(it.message+it)
        emit(ResultWrapper(ResultWrapper.ResultStatus.Failure, exception = it))
    }.onSuccess {
        emit(ResultWrapper(ResultWrapper.ResultStatus.Success, response = it))
    }
}

inline fun <reified T : Any> Flow<ResultWrapper>.launchJsonCallback(
    viewModelScope: CoroutineScope,
    callback: ResultWrapper.JsonWrapperResultCallback<T>){
    launch(viewModelScope,{callback.onLoading()},{callback.onFailure(it)},{ response->
        response.parseJson(onFailure = {callback.onFailure(it)}){ jsonModel->
            callback.onSuccess(jsonModel.code,jsonModel.msg,jsonModel.getData())
        }
    })
}
 fun  Flow<ResultWrapper>.launchStringCallback(
    viewModelScope: CoroutineScope,
    callback: ResultWrapper.StringResultCallback){
    launch(viewModelScope,{callback.onLoading()},{callback.onFailure(it)},{ response->
        response.parseString(onFailure = {callback.onFailure(it)}){ string->
            callback.onSuccess(string)
        }
    })
}


suspend inline fun  HttpResponse.parseString(
    onFailure: ((Throwable) -> Unit),
    onSuccess: ((String) -> Unit)
) {
    safeParse(onFailure, onSuccess){
        body<String>()
    }
}

suspend inline fun  HttpResponse.parseJson(
    onFailure: ((Throwable) -> Unit),
    onSuccess: ((JsonWrapper) -> Unit)
) {
    safeParse(onFailure, onSuccess){
        body<JsonWrapper>()
    }
}

suspend inline fun <reified T>HttpResponse.safeParse( onFailure: ((Throwable) -> Unit),
                                                      onSuccess: ((T) -> Unit),
                                                      parseJson: () -> T){
    val dataDetail = runCatching {
        parseJson.invoke()
    }.onFailure {
        onFailure.invoke(it)
    }.getOrNull()
    if (dataDetail != null) {
        onSuccess.invoke(dataDetail)
    } else {
        val exception = JsonConvertException("parseJson data be blank")
        onFailure.invoke(exception)
    }
}


fun Flow<ResultWrapper>.launch(
    viewModelScope: CoroutineScope,
    onLoading: (() -> Unit)?,
    onFailure: ((Throwable) -> Unit)?,
    onSuccess: suspend ((HttpResponse) -> Unit)
) {
    onEach { result ->
        when (result.status) {
            ResultWrapper.ResultStatus.Loading -> {
                onLoading?.invoke()
            }

            ResultWrapper.ResultStatus.Success -> {
                if (result.response != null) {
                    onSuccess(result.response!!)
                } else {
                    onFailure?.invoke(NullPointerException("Response body is null"))
                }
            }

            ResultWrapper.ResultStatus.Failure -> {
                onFailure?.invoke(result.exception)
            }

            ResultWrapper.ResultStatus.Default -> {
//                Log.e(Constant.TAG, "Launch-Default")
            }
        }
    }
        .launchIn(viewModelScope)
}




