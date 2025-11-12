package kokoro.multiplatform.sasanqua.ui.common.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState

/**
 * Created by xianjie on 2025年1月20日15:38:19
 *
 * Description:Screen 默认处理  类似于以前的BaseActivity
 */
@Composable
inline fun <reified T: BaseUIViewModel<*, *>>VMStateContainer(
    viewModel: T,
    crossinline onInit:suspend (T) -> Unit,
    crossinline effectCallBack: UIContract.UIEffect.()->Unit,
    content: @Composable () -> Unit,
) {
    //第一次加载时执行
    LaunchedEffect(Unit) {
        viewModel.initializeData()
        onInit.invoke(viewModel)
        //Effect注册及默认处理
        viewModel.effectFlow.collect { event->
            when (event) {
                is UIContract.UIEffect.ShowToast -> {
//                    context.toast(event.message)
                }
                is UIContract.UIEffect.NavigateWebScreen -> {
//                    uiManage.navigateTo(Screen.Web.createRoute(event.url, event.titleName))
                }
                is UIContract.UIEffect.NavigateScreen ->{
//                    uiManage.navigateTo(event.route,event.popUpToRoute,event.inclusive,event.launchSingleTop,event.saveState,event.restoreState,event.clearAll)
                }
                else -> effectCallBack.invoke(event)
            }
        }
    }

    content.invoke()
}


