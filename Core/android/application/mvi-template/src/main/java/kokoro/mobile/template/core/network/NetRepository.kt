package kokoro.mobile.template.core.network

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kokoro.mobile.template.core.network.collection.ApiService
import kokoro.mobile.template.model.ResponseBodyWrapper
import kokoro.mobile.network.Network
import kokoro.mobile.network.dispose.fetch
import kokoro.mobile.network.dispose.launch
import kokoro.mobile.network.dispose.pack.Result
import kokoro.mobile.network.dispose.parseJson

/**
 * Created by xianjie on 2025年1月17日17:57:02
 *
 * Description:
 */

class NetRepository(application: Application) : AndroidViewModel(application) {
    private val apiService: kokoro.mobile.template.core.network.collection.ApiService by lazy {
        Network.Companion.getApi(_root_ide_package_.kokoro.mobile.template.core.network.collection.ApiService::class.java)
    }


    infix fun test(callback: Result.Callback<String>){
        fetch {
            apiService.test()
        }.launch(viewModelScope,callback.onLoading,callback.onFailure,{ response->
            response.parseJson<kokoro.mobile.template.model.ResponseBodyWrapper<String>>(onFailure = callback.onFailure?:{}){ jsonModel->
                callback.onSuccess(jsonModel.msg,jsonModel.data)
            }
        })
    }
}