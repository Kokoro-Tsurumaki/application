package kokoro.mobile.template.ui.features.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kokoro.mobile.template.UserDataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by xianjie on 2025年1月7日17:58:14
 *
 * Description:
 */
@HiltViewModel
class WelcomeViewModel @Inject constructor(private val userDataManager: kokoro.mobile.template.UserDataManager) :ViewModel(){
    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            userDataManager.isLoggedIn.collect { isLoggedIn ->
                _navigationEvent.emit(
                if (isLoggedIn) NavigationEvent.NavigateToMain
                    else NavigationEvent.NavigateToLogin
                )
            }
        }
    }
}

sealed class NavigationEvent {
    data object NavigateToMain : NavigationEvent()
    data object NavigateToLogin : NavigationEvent()
}