package kokoro.multiplatform.sasanqua.ui.common.base


/**
 * Created by xianjie on 2025年1月17日23:51:55
 *
 * Description:
 *
 * [UIContract.UIAction]:Screen流向Viewmodel的事件 一般是点击事件、数据更新事件(由UI引起的)、跳转事件
 *
 * [UIContract.UIEffect]:ViewModel流向Screen的事件 一般是依赖于context的UI行为 默认的UIEffect实现了toast与跳转
 *
 * [UIContract.UIState]:UI展示的数据和状态 响应式更新
 *
 * [uiFlow]:将UIState的更新操作委托于AgentUIStateFlow
 */
interface UIContract {
    interface UIState  // 作为标记接口

    //从ViewModel指向Screen的事件
    sealed interface UIEffect {
        data class ShowToast(val message: String) : UIEffect
        data class NavigateWebScreen(val url: String, val titleName: String) : UIEffect
        data class NavigateScreen(
            val route: String,
            val popUpToRoute: String?,
            val inclusive: Boolean,
            val launchSingleTop: Boolean,
            val saveState: Boolean,
            val restoreState: Boolean,
            val clearAll: Boolean
        ) : UIEffect
    }

    //从Screen指向ViewModel的事件
    interface UIAction
}
