package kokoro.mobile.hachimi.ui.features.main.mine

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kokoro.mobile.hachimi.ui.common.base.BaseUIViewModel
import kokoro.mobile.hachimi.core.network.NetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kokoro.mobile.hachimi.UserDataManager
import kokoro.mobile.hachimi.ui.AppEvent
import kokoro.mobile.hachimi.ui.AppViewModel
import kokoro.mobile.hachimi.ui.EventBus
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * =================================================================================================
 * FILE GENERATED AUTOMATICALLY BY GRADLE TASK
 * Generated on: Wed Sep 24 11:33:39 CST 2025
 * Command:`./gradlew createTemplateForMVI -Ptflag=Mine -Ptpackage=kokoro.mobile.hachimi -Ptpath=application/bad-cat/src/main/java/kokoro/mobile/hachimi/ui/features`
 * Module: Mine
 * Package: kokoro.mobile.hachimi
 * =================================================================================================
 */

 @HiltViewModel
 class MineViewModel @Inject constructor(private val currentRepository: NetRepository,private val userDataManager: UserDataManager) :
     BaseUIViewModel<MineContract.MineState, MineContract.MineAction>(MineContract.MineState()) {


     override fun initializeData() {
         viewModelScope.launch {
             userDataManager.getTheme.collect { theme ->
                 Log.e("TAG", "initializeData: "+theme )
                 updateUIState { copy(isDarkTheme = theme == "Dark")}
             }
         }
     }

     override fun onUIAction(event: MineContract.MineAction) {
        when(event){
            MineContract.MineAction.ToggleTheme -> {
                userDataManager.setTheme(viewModelScope,if (uiFlow.value.isDarkTheme)"Light" else "Dark"){

                }
            }
            MineContract.MineAction.Logout -> userDataManager.clearToken(viewModelScope)
            else ->{}
        }
     }
 }