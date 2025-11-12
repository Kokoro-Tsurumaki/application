package kokoro.multiplatform.sasanqua

/**
 * Created by xianjie on 2025年1月21日15:51:26
 *
 * Description:
 */
interface Stack {
    fun popBackStack()
    fun navigateUp()
    fun navigateTo(
        route: String,
        popUpToRoute: String? = null,
        inclusive: Boolean = false,
        launchSingleTop: Boolean = false,
        saveState: Boolean = false,
        restoreState: Boolean = false,
        clearAll:Boolean = false
    )
}