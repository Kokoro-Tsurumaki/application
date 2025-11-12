package kokoro.multiplatform.sasanqua

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShieldMoon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.*
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass
import kokoro.multiplatform.sasanqua.network.client.ApplicationApi
import kokoro.multiplatform.sasanqua.theme.default.*
import kokoro.multiplatform.sasanqua.ui.features.main.*
import kokoro.multiplatform.sasanqua.ui.widthSize
import org.jetbrains.compose.ui.tooling.preview.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@Composable
@Preview
fun App() {
    println("AppRoot")
    //导航管理
    val navController = rememberNavController()
    //导航管理依赖注入
    val navigationManager = remember { LauncherUIManageImpl(navController) }


    module {
        singleOf(::ApplicationApi) { bind<ApplicationApi>() }
    }

    val theme = remember { mutableStateOf("Auto") }
    AppTheme(theme.value) {
        println("AppRootLoad")
        if (isWeb) {
            WebFramework(theme) {
                MainScreen()
            }
        } else {
            MainScreen()
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebFramework(theme: MutableState<String>, content: @Composable () -> Unit) {
    val windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val currentWindowSize: IntSize = currentWindowSize()
    val current = windowSizeClass.widthSize()
    Column(
        modifier = Modifier
            .safeContentPadding()
            .fillMaxSize().verticalScroll(ScrollState(0))
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text("Small Top App Bar")
            },
            actions = {
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        imageVector = Icons.Outlined.ShieldMoon,
                        contentDescription = "Localized description"
                    )
                }
            },
        )
        Box(modifier = Modifier.fillMaxWidth().height(currentWindowSize.height.dp)) {
            content.invoke()
        }
        Box(
            modifier = Modifier.fillMaxWidth().height(140.dp).background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Text(
                "© 2025 kokoro - Content licensed under the CC BY-NC 4.0\n" +
                        "xianjie.zhang516@gmail.com 苏ICP备2025171499号",
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                modifier = Modifier
                    .wrapContentSize().align(Alignment.Center),
            )
        }
    }
}

// 预览专用的假 Owner
class PreviewOwner : ViewModelStoreOwner {
    override val viewModelStore = ViewModelStore()
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
