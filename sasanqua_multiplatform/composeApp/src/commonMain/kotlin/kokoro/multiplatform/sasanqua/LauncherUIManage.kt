package kokoro.multiplatform.sasanqua

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

/**
 * Created by xianjie on 2025年1月21日15:49:13
 *
 * Description:
 */
object LauncherUIManage {
    //staticCompositionLocalOf 值改变时不触发重组，一般用于不常变化的值，如主题导航。
    //与此相对还有一个compositionLocalOf 值改变时触发使用处重组，适用于经常变化的值，如当前颜色
    private val Local = staticCompositionLocalOf<LauncherUIManageImpl> {
        error("No LauncherUIManage provided")
    }

    //获取提供的值
    val current: LauncherUIManageImpl
        @Composable
        get() = Local.current

    //中缀表达式 类似于 provides LauncherUIManageImpl()   ->    provides(LauncherUIManageImpl())
    infix fun provides(navController: LauncherUIManageImpl) = Local provides navController
}

class LauncherUIManageImpl(
    private val navController: NavHostController
) : Stack {

    /**
     * 返回到指定目的地 直接弹出栈顶
     * 直接操作返回栈
     * 简单地弹出栈顶页面
     * 不考虑特殊的导航行为
     * 通常用于代码中的返回逻辑控制
     */
    override fun popBackStack() {
        navController.popBackStack()
    }

    /**
     * 返回上一页 处理系统返回行为
     * 遵循系统的返回导航行为
     * 会考虑 AppBarConfiguration 的设置
     * 可以处理自定义的返回逻辑
     * 通常用于响应 Up 按钮（标题栏返回箭头）的点击
     */
    override  fun navigateUp() {
        navController.navigateUp()
    }

    /**
    * 封装的标准导航方法，提供精细化路由控制能力
    *
    * 使用场景：
    * 1. 普通页面跳转（A→B）
    * 2. 返回栈管理（如登出后清空栈）
    * 3. 单例模式页面（如防止重复打开详情页）
    *
    * @param route 目标页面路由路径，对应NavGraph中的composable路由声明
    *             （例如："home/profile"）
    *
    * @param popUpToRoute 需要从返回栈中弹出的目标路由。当不为null时，
    *                    会先弹出到该路由所在位置，再执行新导航。
    *                    典型场景：登录成功后清除所有登录相关页面
    *                    （例如：弹出到"main"，移除中间的"splash/login"）
    *
    * @param inclusive 是否包含popUpToRoute指定的页面本身。
    *                - true：连同popUpToRoute页面一起弹出（如退出整个模块）
    *                - false：保留popUpToRoute页面（如返回主页但保留主页实例）
    *                示例：当popUpToRoute="login"时：
    *                true→清空到login前；false→保留login
    *
    * @param launchSingleTop 是否启用"单例模式"导航：
    *                      - true：如果目标页面已在栈顶，则不再创建新实例
    *                      - false：总是创建新实例
    *                      典型用例：防止重复打开同一个商品详情页
    *
    * @param saveState 弹出页面时是否保存其状态。
    *                 - true：页面被弹出时保留状态（返回时可恢复）
    *                 - false：不保存状态（每次进入都是初始状态）
    *                 注意：需要配合restoreState使用
    *
    * @param restoreState 是否恢复目标页面之前保存的状态。
    *                    - true：尝试恢复之前状态（需saveState=true）
    *                    - false：总是初始化新状态
    *                    典型场景：电商列表页返回时恢复滚动位置
    *
    * @param clearAll 是否完全清空返回栈再导航（相当于冷启动目标页）。
    *               - true：先清空所有历史栈，再导航到目标页
    *               - false：正常叠加导航
    *               特殊场景：用户登出后跳登录页，不允许回退
    *
    * 典型使用示例：
    * 1. 普通跳转（A→B）:
    *    navigateTo("pageB")
    *
    * 2. 登录后跳主页并清空栈:
    *    navigateTo(
    *        route = "home",
    *        popUpToRoute = "splash",
    *        inclusive = true,
    *        clearAll = true
    *    )
    *
    * 3. 打开详情页（单例模式）:
    *    navigateTo(
    *        route = "detail/$id",
    *        launchSingleTop = true
    *    )
    *
    * 4. 返回主页并保留状态:
    *    navigateTo(
    *        route = "home",
    *        popUpToRoute = "home",
    *        inclusive = false,
    *        restoreState = true
    *    )
    */
    override fun navigateTo(
        route: String,
        popUpToRoute: String?,
        inclusive: Boolean,
        launchSingleTop: Boolean,
        saveState: Boolean,
        restoreState: Boolean,
        clearAll: Boolean
    ) {
        navController.navigate(route) {
            if (popUpToRoute != null) {
                popUpTo(popUpToRoute) {
                    this.inclusive = inclusive
                    this.saveState = saveState
                }
            }
            if (launchSingleTop) {
                this.launchSingleTop = true
            }
            if (clearAll) {
                popUpTo(0) // 清空返回栈
            }
            if (restoreState) {
                this.restoreState = true
            }
        }
    }

}