package kokoro.mobile.template.ui.features.main

import androidx.lifecycle.ViewModel
import kokoro.mobile.template.Constant
import kokoro.mobile.template.R
import kokoro.mobile.template.ui.features.main.bottom_layout.BottomLayoutData
import kokoro.mobile.template.ui.features.main.bottom_layout.BottomLayoutEvent
import kokoro.mobile.template.ui.features.main.bottom_layout.BottomLayoutState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Created by xianjie on 2025年1月7日10:59:08
 *
 * Description:
 */
class MainViewModel : ViewModel(){
    private val tabData = MutableStateFlow(
        _root_ide_package_.kokoro.mobile.template.ui.features.main.bottom_layout.BottomLayoutState(
            selectedKey = _root_ide_package_.kokoro.mobile.template.Constant.HOME_TYPE_RUNNING,
            items = mapOf(
                _root_ide_package_.kokoro.mobile.template.Constant.HOME_TYPE_RUNNING to _root_ide_package_.kokoro.mobile.template.ui.features.main.bottom_layout.BottomLayoutData(
                    _root_ide_package_.kokoro.mobile.template.Constant.HOME_TYPE_RUNNING,
                    "首页",
                    R.drawable.drawable_main_sy,
                    23,
                    22
                ),
                _root_ide_package_.kokoro.mobile.template.Constant.HOME_TYPE_SETTING to _root_ide_package_.kokoro.mobile.template.ui.features.main.bottom_layout.BottomLayoutData(
                    _root_ide_package_.kokoro.mobile.template.Constant.HOME_TYPE_SETTING,
                    "云学院",
                    R.drawable.drawable_main_yxy,
                    26,
                    21
                ),
                _root_ide_package_.kokoro.mobile.template.Constant.HOME_TYPE_DETAIL to _root_ide_package_.kokoro.mobile.template.ui.features.main.bottom_layout.BottomLayoutData(
                    _root_ide_package_.kokoro.mobile.template.Constant.HOME_TYPE_DETAIL,
                    "消息",
                    R.drawable.drawable_main_xx,
                    20,
                    20
                ),
                _root_ide_package_.kokoro.mobile.template.Constant.HOME_TYPE_INFO to _root_ide_package_.kokoro.mobile.template.ui.features.main.bottom_layout.BottomLayoutData(
                    _root_ide_package_.kokoro.mobile.template.Constant.HOME_TYPE_INFO,
                    "我的",
                    R.drawable.drawable_main_wd,
                    23,
                    22
                )
            )
        )
    )
    //    @Inject
    val tabState = tabData.asStateFlow()

    fun handleEvent(event: kokoro.mobile.template.ui.features.main.bottom_layout.BottomLayoutEvent) {
        when (event) {
            is kokoro.mobile.template.ui.features.main.bottom_layout.BottomLayoutEvent.OnTabSelected -> {
                tabData.update { it.copy(selectedKey = event.key) }
            }
        }
    }
}