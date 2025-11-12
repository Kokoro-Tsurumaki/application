package kokoro.mobile.hachimi.ui.features.login

import androidx.lifecycle.viewModelScope
import kokoro.mobile.hachimi.core.network.NetRepository
import kokoro.mobile.hachimi.ui.common.base.BaseUIViewModel
import kokoro.mobile.hachimi.ui.features.login.LoginUIContract.LoginAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kokoro.mobile.hachimi.UserDataManager
import kokoro.mobile.hachimi.ui.Screen
import kokoro.mobile.network.handle.pack.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by xianjie on 2025年1月9日02:19:55
 *
 * Description:
 */

@HiltViewModel
class LoginViewModel @Inject constructor(private val currentRepository: NetRepository,private val userDataManager: UserDataManager) :
    BaseUIViewModel<LoginUIContract.LoginState, LoginAction>(LoginUIContract.LoginState()) {


    override fun initializeData() {

//        currentRepository.getVerificationImage()
    }

    override fun onUIAction(event: LoginAction) {
        when(event){
            is LoginAction.OnAuthUpdate ->  updateUIState { copy(authCode = event.value) }
            LoginAction.OnCheckUpdate -> updateUIState { copy(checked = !uiFlow.value.checked) }
            LoginAction.OnImageAuthClick -> TODO()
            is LoginAction.OnImageAuthUpdate -> updateUIState { copy(authImageCode = event.value) }
            LoginAction.OnImageClick -> {
                //                currentRepository.getVerificationImage()
            }
            LoginAction.OnLoginClick -> {
                val checkString = uiFlow.value.checkInput()
                if (checkString.isBlank()){
                    currentRepository.login(uiFlow.value.userName,uiFlow.value.password,object:Result.JsonWrapperResultCallback<LoginUIContract.LoginResponse>{
                        override fun onLoading() {

                        }

                        override fun onSuccess(msg: String, data: LoginUIContract.LoginResponse) {
                            userDataManager.saveToken(viewModelScope,data.token,data.userId){
                                navigateScreen(Screen.Welcome.route,Screen.Authorize.route,true)
                            }
                        }

                        override fun onFailure(t: Throwable) {
                            showToast(t.message?:"")
                        }

                    })
                }else{
                    showToast(checkString)
                }

            }
            is LoginAction.OnPasswordUpdate ->  updateUIState { copy(password = event.value) }
            is LoginAction.OnPhoneNumberUpdate -> updateUIState { copy(phoneNumber = event.value) }
            LoginAction.OnPrivacyPolicyClick ->  navigateWebScreen("file:android_asset/agreementone.html","隐私协议")
            LoginAction.OnTypeClick ->  updateUIState { copy(type = !uiFlow.value.type) }
            LoginAction.OnUserAgreementClick -> navigateWebScreen("file:android_asset/agreement.html","用户服务")
            is LoginAction.OnUserNameUpdate -> updateUIState { copy(userName = event.value) }
        }
    }


    //验证码倒计时
    private fun countDown() {
        updateUIState { copy(time = 60) }
        viewModelScope.launch(Dispatchers.IO) {
            while (uiFlow.value.time > 0) {
                updateUIState { copy(time = uiFlow.value.time - 1) }
                delay(1000)
            }
        }
    }
}