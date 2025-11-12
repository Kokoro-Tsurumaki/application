package kokoro.mobile.template.ui.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kokoro.mobile.network.dispose.pack.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by xianjie on 2025年1月14日18:17:32
 *
 * Description:
 *
 * [ShareRepository]:网络请求分离类
 *
 * [uiFlow]:uiState流 ui展示的data与状态
 *
 * 更新：
 * @see updateUIState()
 *
 * 响应:
 * @see uiFlow.collectAsStateWithLifecycle() 在对应Screen调用获取
 *
 * [effectFlow]:ViewModel流向UI，因此收集应处于UI层，发送处于ViewModel层
 *
 * 发送：
 *
 * 响应及处理：
 * @see VMStateContainer.effectCallBack
 *
 * [actionFlow]:UI流向ViewModel
 *
 * 发送
 * sendUIAction
 *
 * 相应及处理：
 * @see onUIAction()
 */
abstract class BaseUIViewModel<S : UIContract.UIState, A : UIContract.UIAction>(
    val uiState: S,
) : ViewModel() {
    //State
    private var _uiFlow: MutableStateFlow<S> = MutableStateFlow(uiState)
    var uiFlow: StateFlow<S> = _uiFlow.asStateFlow()

    //Effect
    private var _effectFlow: Channel<UIContract.UIEffect> = Channel<UIContract.UIEffect>()
    var effectFlow: Flow<UIContract.UIEffect> = _effectFlow.receiveAsFlow()

    //Action
    private var _actionFlow: Channel<A> = Channel<A>()
    var actionFlow: Flow<A> = _actionFlow.receiveAsFlow()

    init {
        viewModelScope.launch {
            actionFlow.collect { event ->
                onUIAction(event)
            }
        }
    }


    //除此加载页面时要请求/加载的数据
    abstract fun initializeData()

    //===============================================//
    //======================Send=====================//
    //===============================================//
    //网络请求 请求

    // 提供更新现有值的方法
    fun updateUIState(block: S.() -> S) {
        // 这里使用 data class 的 copy 方法
        _uiFlow.update {
            block.invoke(it)
        }
    }

    //提供发送Action事件的方法
    fun sendUIAction(event:A){
        viewModelScope.launch {
            _actionFlow.send(event)
        }
    }

    //===============================================//
    //===================Collect=====================//
    //===============================================//

    //从NetWork->ViewModel Flow的回调
    abstract fun onNetworkResponse(event: Result)

    //从Screen->ViewModel 高阶方法的回调
    abstract fun onUIAction(event: A)

    fun showToast(message: String) {
        viewModelScope.launch {
            _effectFlow.send(UIContract.UIEffect.ShowToast(message))
        }
    }

    fun navigateWebScreen(url: String, titleName: String) {
        viewModelScope.launch {
            _effectFlow.send(UIContract.UIEffect.NavigateWebScreen(url, titleName))
        }
    }

    fun navigateScreen(
        route: String,
        popUpToRoute: String? = null,
        inclusive: Boolean = false,
        launchSingleTop: Boolean = false,
        saveState: Boolean = false,
        restoreState: Boolean = false,
        clearAll: Boolean = false
    ) {
        viewModelScope.launch {
            _effectFlow.send(UIContract.UIEffect.NavigateScreen(route, popUpToRoute, inclusive, launchSingleTop, saveState, restoreState, clearAll))
        }
    }


    inline fun <reified E> UIContract.UIAction.onEvent(event: (E.() -> Unit)) {
        if (this is E) {
            event.invoke(this)
        }
    }
}
