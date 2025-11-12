package kokoro.mobile.hachimi.core.network

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kokoro.mobile.hachimi.core.network.collection.ApiService
import kokoro.mobile.hachimi.ui.features.login.LoginUIContract
import kokoro.mobile.network.Network
import kokoro.mobile.network.handle.fetch
import kokoro.mobile.network.handle.launchWithCallback
import kokoro.mobile.network.handle.pack.Result
import kokoro.mobile.network.handle.toJson


/**
 * Created by xianjie on 2025年1月17日17:57:02
 *
 * Description:
 */

class NetRepository(application: Application) : AndroidViewModel(application) {
    private val apiService: ApiService by lazy {
        Network.getApi(ApiService::class.java)
    }

     fun test(callback: Result.JsonWrapperResultCallback<String>){
        fetch {
            apiService.test()
        }.launchWithCallback(viewModelScope,callback)
    }

    fun getAuthorizeHint(callback: Result.JsonWrapperResultCallback<String>){
        fetch {
            apiService.getAuthorizeHint()
        }.launchWithCallback(viewModelScope,callback)
    }

    fun login(username:String,password:String,callback: Result.JsonWrapperResultCallback<LoginUIContract.LoginResponse>){
        fetch {
            apiService.login(hashMapOf<String,Any>("username" to username,"password" to password).toJson())
        }.launchWithCallback(viewModelScope,callback)
    }
}