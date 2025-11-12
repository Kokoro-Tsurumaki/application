package kokoro.mobile.template.core

import kokoro.mobile.template.model.NetLogModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Created by xianjie on 2025年1月16日20:30:08
 *
 * Description:
 */
object NetLogDataManage {
    private val _items = MutableStateFlow <List<kokoro.mobile.template.model.NetLogModel>>(emptyList())
    private val items = _items.asStateFlow()

    fun addLog(log: kokoro.mobile.template.model.NetLogModel){
        _items.update { currentList->
            // 检查是否存在相同时间戳的日志
            val exists = currentList.any { it.startTime == log.startTime }
            if (exists) {
                // 如果存在，更新它
                currentList.map { if (it.startTime == log.startTime) log else it }
            } else {
                // 如果不存在，添加到列表开头
                listOf(log) + currentList
            }
        }
    }

    fun getLog() = items
}