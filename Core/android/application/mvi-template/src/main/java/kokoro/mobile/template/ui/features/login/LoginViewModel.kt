package kokoro.mobile.template.ui.features.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kokoro.mobile.network.dispose.pack.Result
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
class LoginViewModel @Inject constructor(private val currentRepository: kokoro.mobile.template.core.network.NetRepository) :
    kokoro.mobile.template.ui.common.base.BaseUIViewModel<LoginUIContract.LoginState, kokoro.mobile.template.ui.features.login.LoginUIContract.LoginAction>(LoginUIContract.LoginState()) {


    override fun initializeData() {
        currentRepository test Result.Callback<String>({
            Log.e("TAG", "initializeData: 加载" )
        },{
            Log.e("TAG", "initializeData: 失败" )
        }){msg,data->
            Log.e("TAG", "initializeData: ${msg}" )
            Log.e("TAG", "initializeData: ${data}" )
        }
//        currentRepository.getVerificationImage()
    }

    override fun onNetworkResponse(event: Result) {

    }

    override fun onUIAction(event: kokoro.mobile.template.ui.features.login.LoginUIContract.LoginAction) {
        event.apply {
            onEvent<kokoro.mobile.template.ui.features.login.LoginUIContract.LoginAction.OnPhoneNumberUpdate> {
                updateUIState { copy(phoneNumber = value) }
            }
            onEvent<kokoro.mobile.template.ui.features.login.LoginUIContract.LoginAction.OnImageAuthUpdate> {
                updateUIState { copy(authImageCode = value) }
            }
            onEvent<kokoro.mobile.template.ui.features.login.LoginUIContract.LoginAction.OnAuthUpdate> {
                updateUIState { copy(authCode = value) }
            }
            onEvent<kokoro.mobile.template.ui.features.login.LoginUIContract.LoginAction.OnImageClick> {
//                currentRepository.getVerificationImage()
            }
            onEvent<kokoro.mobile.template.ui.features.login.LoginUIContract.LoginAction.OnLoginClick> {
                val success = uiFlow.value.checkInput()
//                when {
//                    success.isNotBlank() -> showToast(success)
//
//                    else ->
//                        if (uiFlow.value.type) currentRepository.loginWithVerification(uiFlow.value.phoneNumber, uiFlow.value.authCode)
//                        else currentRepository.loginWithPassword(uiFlow.value.userName, uiFlow.value.password)
//                }
            }
            onEvent<kokoro.mobile.template.ui.features.login.LoginUIContract.LoginAction.OnImageAuthClick> {
//                val success = uiFlow.value.checkInput()
//                when {
//                    success.isNotBlank() -> showToast(success)
//
//                    else -> currentRepository.getImageVerification(uiFlow.value.authImageCode, uiFlow.value.imageCode)
//                }
            }
            onEvent<kokoro.mobile.template.ui.features.login.LoginUIContract.LoginAction.OnTypeClick> {
                updateUIState { copy(type = !uiFlow.value.type) }
            }
            onEvent<kokoro.mobile.template.ui.features.login.LoginUIContract.LoginAction.OnUserNameUpdate> {
                updateUIState { copy(userName = value) }
            }
            onEvent<kokoro.mobile.template.ui.features.login.LoginUIContract.LoginAction.OnPasswordUpdate> {
                updateUIState { copy(password = value) }
            }
            onEvent<kokoro.mobile.template.ui.features.login.LoginUIContract.LoginAction.OnCheckUpdate> {
                updateUIState { copy(checked = !uiFlow.value.checked) }
            }
            onEvent<kokoro.mobile.template.ui.features.login.LoginUIContract.LoginAction.OnPrivacyPolicyClick> {
                navigateWebScreen("file:android_asset/agreementone.html","隐私协议")
            }
            onEvent<kokoro.mobile.template.ui.features.login.LoginUIContract.LoginAction.OnUserAgreementClick> {
                navigateWebScreen("file:android_asset/agreement.html","用户服务")
            }
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