package kokoro.mobile.hachimi.ui.features.main.mine

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import kokoro.mobile.hachimi.ui.common.base.UIContract

/**
 * =================================================================================================
 * FILE GENERATED AUTOMATICALLY BY GRADLE TASK
 * Generated on: Wed Sep 24 11:33:39 CST 2025
 * Command:`./gradlew createTemplateForMVI -Ptflag=Mine -Ptpackage=kokoro.mobile.hachimi -Ptpath=application/bad-cat/src/main/java/kokoro/mobile/hachimi/ui/features`
 * Module: Mine
 * Package: kokoro.mobile.hachimi
 * =================================================================================================
 */

class MineContract:UIContract {
    data class MineState(
        var createTime:String = "Wed Sep 24 11:33:39 CST 2025",
        val avatarUrl: String? = null,
        val nickname: String = "未登录",
        val isDarkTheme: Boolean = false,
        val functions: List<MineFunction> = listOf(
            MineFunction(
                icon = Icons.Default.Info,
                title = "设置",
                action = MineAction.NavigateToSettings
            ),
            MineFunction(
                icon = Icons.Default.Info,
                title = "通知设置",
                action = MineAction.NavigateToNotificationSettings
            ),
            MineFunction(
                icon = Icons.Default.Info,
                title = "隐私设置",
                action = MineAction.NavigateToPrivacySettings
            ),
            MineFunction(
                icon = Icons.Default.Info,
                title = "关于我们",
                action = MineAction.NavigateToAbout
            ),
            MineFunction(
                icon = Icons.Default.Info,
                title = "帮助中心",
                action = MineAction.NavigateToHelp
            )
    )): UIContract.UIState

    sealed class MineAction: UIContract.UIAction {
        object ToggleTheme : MineAction()
        object NavigateToProfile : MineAction()
        object NavigateToSettings : MineAction()
        object NavigateToNotificationSettings : MineAction()
        object NavigateToPrivacySettings : MineAction()
        object NavigateToAbout : MineAction()
        object NavigateToHelp : MineAction()
        object Logout : MineAction()
    }

    data class MineFunction(
        val icon: ImageVector,
        val title: String,
        val action: MineAction
    )
}