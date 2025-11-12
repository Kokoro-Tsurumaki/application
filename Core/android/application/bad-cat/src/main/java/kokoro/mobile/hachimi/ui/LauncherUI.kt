package kokoro.mobile.hachimi.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import kokoro.mobile.hachimi.theme.default.AppTheme
import kokoro.mobile.hachimi.ui.common.base.BaseActivity
import kokoro.mobile.hachimi.ui.features.authorize.AuthorizeScreen
import kokoro.mobile.hachimi.ui.features.login.LoginScreen
import kokoro.mobile.hachimi.ui.features.main.MainScreen
import kokoro.mobile.hachimi.ui.features.main.mine.MineViewModel
import kokoro.mobile.hachimi.ui.features.web.WebScreen
import kokoro.mobile.hachimi.ui.features.welcome.NavigationEvent
import kokoro.mobile.hachimi.ui.features.welcome.WelcomeScreen
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class LauncherUI : BaseActivity() {

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

            val viewModel: AppViewModel = viewModel()
            val theme = remember { mutableStateOf("") }
            APP(navigationManager, navController,theme)
            //捕获全局事件
            LaunchedEffect(Unit) {

//                requestOverlayPermission{
//
//                }
                lifecycleScope.launch {
                    viewModel.appEvent.collect { event ->
                        when (event) {
                            is AppViewModel.AppEvent.ThemeChanged -> {
                                Timber.e(event.theme)
                                theme.value = event.theme
                                Timber.e(theme.value)
                            }
                        }
                    }
                }
                lifecycleScope.launch {
                    EventBus.events.collect { event ->
                        when (event) {
                            AppEvent.ResetApp -> {
                                navigationManager.navigateTo("welcome", clearAll = true)
                            }

                            AppEvent.FinishApp -> finish()
                            is AppEvent.ChangeTheme -> {

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
    navigationManager: LauncherUIManageImpl,
    navController: NavHostController,
    theme: MutableState<String>
) {
    //提供值
    CompositionLocalProvider(
        LauncherUIManage provides navigationManager
    ) {
        AppTheme(theme.value) {
                NavHost(
                    navController = navController,
                    startDestination = "welcome"
                ) {
                    //欢迎页
                    composable(Screen.Welcome.route) {
                        WelcomeScreen(onNavigateToMain = {
                            navController.navigate(Screen.Main.route) {
                                popUpTo(Screen.Welcome.route) { inclusive = true }
                            }
                        }, onNavigateToLogin = {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Welcome.route) { inclusive = true }
                            }
                        }, onNavigateToAuthorize = {
                            navController.navigate(Screen.Authorize.route) {
                                popUpTo(Screen.Welcome.route) { inclusive = true }
                            }
                        })
                    }

                    //主页
                    composable(Screen.Main.route) {
                        MainScreen()
                    }
                    //登录
                    composable(Screen.Login.route) {
                        LoginScreen()
                    }
                    composable(Screen.Authorize.route) {
                        AuthorizeScreen()

                    }
                    //Web
                    composable(
                        Screen.Web.route, arguments = listOf(
                            navArgument("url") { type = NavType.StringType },
                            navArgument("name") { type = NavType.StringType }
                        )) { backStackEntry ->
                        val url = backStackEntry.arguments?.getString("url") ?: ""
                        val name = backStackEntry.arguments?.getString("name") ?: ""
                        WebScreen(url, name)
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
        AppTheme {
            content()
        }
    }
}

// 预览专用的假 Owner
class PreviewOwner : ViewModelStoreOwner {
    override val viewModelStore = ViewModelStore()
}

