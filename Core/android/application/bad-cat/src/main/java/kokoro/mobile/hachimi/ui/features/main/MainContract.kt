package kokoro.mobile.hachimi.ui.features.main

/**
 * Created by xianjie on 2025年1月3日20:32:35
 *
 * Description:
 */

data class MainTabState (
    val selectedKey: Int = 0,
    val items: List<String> = listOf("记账", "明细", "规划", "发现", "我的")
)
sealed class MainTabEvent {
    data class OnTabSelected(val key: Int) : MainTabEvent()
}