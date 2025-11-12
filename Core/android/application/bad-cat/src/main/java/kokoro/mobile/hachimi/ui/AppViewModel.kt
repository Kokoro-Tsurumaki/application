package kokoro.mobile.hachimi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kokoro.mobile.hachimi.UserDataManager
import kokoro.mobile.hachimi.ui.features.welcome.NavigationEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val userDataManager: UserDataManager) : ViewModel() {
    private val _appEvent = MutableSharedFlow<AppEvent>()
    val appEvent = _appEvent.asSharedFlow()

    init {
        checkThemeStatus()
    }
    private fun checkThemeStatus() {
        viewModelScope.launch {
            userDataManager.getTheme.collect { theme ->
                _appEvent.emit(
                    AppEvent.ThemeChanged(theme)
                )
            }
        }
    }
    sealed class AppEvent {
        class ThemeChanged(val theme:String):AppEvent()
    }
}