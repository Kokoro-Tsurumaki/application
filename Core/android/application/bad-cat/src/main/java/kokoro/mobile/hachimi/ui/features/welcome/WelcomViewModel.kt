package kokoro.mobile.hachimi.ui.features.welcome

import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kokoro.mobile.hachimi.UserDataManager
import kokoro.mobile.hachimi.database.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * Created by xianjie on 2025年1月7日17:58:14
 *
 * Description:
 */
@HiltViewModel
class WelcomeViewModel @Inject constructor(private val userDataManager: UserDataManager,private val databaseRepository: DatabaseRepository) :ViewModel(){
    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        checkAuthorizeStatus()
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
    private fun checkAuthorizeStatus() {
        viewModelScope.launch {
            userDataManager.isAgreeAuthorize.collect { agreeAuthorize ->
                if (agreeAuthorize)checkLoginStatus()
                else _navigationEvent.emit( NavigationEvent.NavigateToAuthorize)
            }
        }
    }
}

sealed class NavigationEvent {
    data object NavigateToMain : NavigationEvent()
    data object NavigateToLogin : NavigationEvent()
    data object NavigateToAuthorize : NavigationEvent()
}