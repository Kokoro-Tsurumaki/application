package kokoro.mobile.hachimi.ui.features.authorize

import kokoro.mobile.hachimi.ui.common.base.UIContract

/**
 * =================================================================================================
 * FILE GENERATED AUTOMATICALLY BY GRADLE TASK
 * Generated on: Wed Sep 17 10:13:34 CST 2025
 * Command:`./gradlew createTemplateForMVI -Ptflag=Authorize -Ptpackage=kokoro.mobile.hachimi -Ptpath=application/bad-cat/src/main/java/kokoro/mobile/hachimi/ui/features`
 * Module: Authorize
 * Package: kokoro.mobile.hachimi
 * =================================================================================================
 */

class AuthorizeContract:UIContract {
    data class AuthorizeState(
        var createTime:String = "Wed Sep 17 10:13:34 CST 2025",
        var hint: String = ""
    ): UIContract.UIState

    sealed class AuthorizeAction: UIContract.UIAction {
        object OnAuthorizeRejectClick: AuthorizeAction()
        object OnAuthorizeAgreeClick: AuthorizeAction()

    }
}