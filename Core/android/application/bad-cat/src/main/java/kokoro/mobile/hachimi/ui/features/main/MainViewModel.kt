package kokoro.mobile.hachimi.ui.features.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Created by xianjie on 2025年1月7日10:59:08
 *
 * Description:
 */
class MainViewModel : ViewModel(){
    private val tabData = MutableStateFlow(MainTabState())

    val tabState = tabData.asStateFlow()

    fun handleEvent(event: MainTabEvent) {
        when (event) {
            is MainTabEvent.OnTabSelected -> {
                tabData.update { it.copy(selectedKey = event.key) }
            }
        }
    }
}