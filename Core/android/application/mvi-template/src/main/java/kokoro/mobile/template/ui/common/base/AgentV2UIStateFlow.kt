package kokoro.mobile.template.ui.common.base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Created by xianjie on 2025年1月23日15:54:10
 *
 * Description:
 */
class AgentV2UIStateFlow<T : UIContract.UIState>(val _dataFlow: MutableStateFlow<T>) : MutableStateFlow<T> by _dataFlow {

    private var dataFlow: StateFlow<T> = _dataFlow.asStateFlow()

    override var value: T  = dataFlow.value

    // 提供更新现有值的方法
    fun update(block: T.() -> T) {
        val currentValue = _dataFlow.value
        // 这里使用 data class 的 copy 方法
        _dataFlow.update {
            block.invoke(it)
        }
    }

    fun observe(): StateFlow<T> = dataFlow
}