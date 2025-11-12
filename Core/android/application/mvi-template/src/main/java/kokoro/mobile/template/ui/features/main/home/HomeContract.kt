package kokoro.mobile.template.ui.features.main.home

import kokoro.mobile.template.ui.common.base.UIContract

/**
 * Description:
 */
class HomeContract : kokoro.mobile.template.ui.common.base.UIContract {
    data class HomeState(val time: String = "") : kokoro.mobile.template.ui.common.base.UIContract.UIState
    sealed class HomeAction() : kokoro.mobile.template.ui.common.base.UIContract.UIAction {

    }
}