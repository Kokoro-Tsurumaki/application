package kokoro.mobile.template.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kokoro.mobile.template.theme.KokoroTheme
import kokoro.mobile.template.ui.features.login.LoginScreen
import kokoro.mobile.template.ui.features.main.MainScreen
import kokoro.mobile.template.ui.features.web.WebScreen
import kokoro.mobile.template.ui.features.welcome.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LauncherUI : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //调试
//            DebugView(lifecycleScope)
            //导航管理
            val navController = rememberNavController()
            //导航管理依赖注入
            val navigationManager = remember { LauncherUIManageImpl(navController) }
            APP(this@LauncherUI, navigationManager, navController)
            //捕获全局事件
            LaunchedEffect(Unit) {
                lifecycleScope.launch {
                    EventBus.events.collect { event ->
                        when (event) {
                            AppEvent.ResetApp -> {
                                navigationManager.navigateTo("welcome", clearAll = true)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun APP(
    context: Context,
    navigationManager: LauncherUIManageImpl,
    navController: NavHostController
) {
    //提供值
    CompositionLocalProvider(
        LauncherUIManage provides navigationManager
    ) {
        _root_ide_package_.kokoro.mobile.template.theme.KokoroTheme {
            NavHost(
                navController = navController,
                startDestination = "welcome"
            ) {
                //欢迎页
                composable(Screen.Welcome.route) {
                    _root_ide_package_.kokoro.mobile.template.ui.features.welcome.WelcomeScreen(
                        onNavigateToMain = {
                            Toast.makeText(context, "登录了", Toast.LENGTH_SHORT).show()
                            navController.navigate(Screen.Main.route) {
                                popUpTo(Screen.Main.route) { inclusive = true }
                            }
                        },
                        onNavigateToLogin = {
                            Toast.makeText(context, "未登录", Toast.LENGTH_SHORT).show()
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        })
                }

                //主页
                composable(Screen.Main.route) {
                    _root_ide_package_.kokoro.mobile.template.ui.features.main.MainScreen()
                }
                //登录
                composable(Screen.Login.route) {
                    _root_ide_package_.kokoro.mobile.template.ui.features.login.LoginScreen()
                }
                //Web
                composable(
                    Screen.Web.route, arguments = listOf(
                        navArgument("url") { type = NavType.StringType },
                        navArgument("name") { type = NavType.StringType }
                    )) { backStackEntry ->
                    val url = backStackEntry.arguments?.getString("url") ?: ""
                    val name = backStackEntry.arguments?.getString("name") ?: ""
                    _root_ide_package_.kokoro.mobile.template.ui.features.web.WebScreen(url, name)
                }
            }
        }
    }
}

@Composable
fun PreviewContainer(
    content: @Composable () -> Unit
) {
    // 注入必要的 CompositionLocal
    CompositionLocalProvider(
        LocalViewModelStoreOwner provides PreviewOwner(),
    ) {
        _root_ide_package_.kokoro.mobile.template.theme.KokoroTheme {
            content()
        }
    }
}

// 预览专用的假 Owner
class PreviewOwner : ViewModelStoreOwner {
    override val viewModelStore = ViewModelStore()
}

