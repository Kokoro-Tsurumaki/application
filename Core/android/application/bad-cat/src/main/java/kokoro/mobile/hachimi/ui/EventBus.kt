package kokoro.mobile.hachimi.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Created by xianjie on 2024年12月30日14:03:52
 *
 * Description:
 */

object EventBus {
    private val _events = Channel<AppEvent>()
    val events = _events.receiveAsFlow() // 对外暴露不可变版本
    fun send(event:AppEvent){
        CoroutineScope(Dispatchers.IO).launch {
            _events.send(event)
            this.cancel()
        }
    }
}
sealed interface AppEvent {
    data object ResetApp : AppEvent
    object FinishApp: AppEvent
    class ChangeTheme(val value: String):AppEvent

}

