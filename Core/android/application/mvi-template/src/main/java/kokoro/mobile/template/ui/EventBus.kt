package kokoro.mobile.template.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Created by xianjie on 2024年12月30日14:03:52
 *
 * Description:
 */

object EventBus : ViewModel() {
    private val _events = Channel<AppEvent>()
    val events = _events.receiveAsFlow() // 对外暴露不可变版本
    suspend fun send(event:AppEvent){
        _events.send(event);
    }
}
sealed class AppEvent {
    data object ResetApp : AppEvent()
}

