package kokoro.mobile.template.ui.features.main.bottom_layout

/**
 * Created by xianjie on 2025年1月3日20:32:35
 *
 * Description:
 */

data class BottomLayoutState (
    val selectedKey: Int,
    val items: Map<Int, BottomLayoutData>
)
sealed class BottomLayoutEvent {
    data class OnTabSelected(val key: Int) : BottomLayoutEvent()
}