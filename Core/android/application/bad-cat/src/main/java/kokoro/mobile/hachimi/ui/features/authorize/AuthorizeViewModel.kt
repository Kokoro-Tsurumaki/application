package kokoro.mobile.hachimi.ui.features.authorize

import android.util.Log
import androidx.lifecycle.viewModelScope
import kokoro.mobile.hachimi.ui.common.base.BaseUIViewModel
import kokoro.mobile.hachimi.core.network.NetRepository
import kokoro.mobile.network.handle.pack.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kokoro.mobile.hachimi.UserDataManager
import kokoro.mobile.hachimi.ui.AppEvent
import kokoro.mobile.hachimi.ui.EventBus
import kokoro.mobile.hachimi.ui.Screen
import javax.inject.Inject

/**
 * =================================================================================================
 * FILE GENERATED AUTOMATICALLY BY GRADLE TASK
 * Generated on: Wed Sep 17 10:13:34 CST 2025
 * Command:`./gradlew createTemplateForMVI -Ptflag=Authorize -Ptpackage=kokoro.mobile.hachimi -Ptpath=application/bad-cat/src/main/java/kokoro/mobile/hachimi/ui/features`
 * Module: Authorize
 * Package: kokoro.mobile.hachimi
 * =================================================================================================
 */

 @HiltViewModel
 class AuthorizeViewModel @Inject constructor(private val currentRepository: NetRepository,private val userDataManager: UserDataManager) :
     BaseUIViewModel<AuthorizeContract.AuthorizeState, AuthorizeContract.AuthorizeAction>(AuthorizeContract.AuthorizeState()) {


     override fun initializeData() {
         Log.e("TAG", "initializeData: ", )
         currentRepository.getAuthorizeHint(object:Result.JsonWrapperResultCallback<String>{
             override fun onLoading() {

             }

             override fun onFailure(t: Throwable) {

             }


             override fun onSuccess(msg: String, data: String) {
                 Log.e("TAG", "onSuccess: "+data )
                 updateUIState { copy(hint = data) }
             }
         })
     }

     override fun onUIAction(event: AuthorizeContract.AuthorizeAction) {
        when(event){
            AuthorizeContract.AuthorizeAction.OnAuthorizeAgreeClick -> {
                userDataManager.agreeAuthorize(viewModelScope){
                    navigateScreen(Screen.Welcome.route,Screen.Authorize.route,true)
                }
            }
            AuthorizeContract.AuthorizeAction.OnAuthorizeRejectClick -> {
                EventBus.send(AppEvent.FinishApp)
            }
        }
     }
 }