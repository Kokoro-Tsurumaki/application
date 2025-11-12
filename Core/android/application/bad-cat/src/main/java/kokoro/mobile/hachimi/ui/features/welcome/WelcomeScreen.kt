package kokoro.mobile.hachimi.ui.features.welcome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kokoro.mobile.hachimi.common.Modifier.scaffoldModifier
import kokoro.mobile.hachimi.ui.PreviewContainer
import kokoro.mobile.hachimi.ui.common.view.specific.HachimiFlag

/**
 * Created by xianjie on 2025年1月7日14:00:58
 *
 * Description:
 */
@Composable
fun WelcomeScreen(
                  onNavigateToLogin: () -> Unit,
                  onNavigateToMain: () -> Unit,
                  onNavigateToAuthorize: () -> Unit){


    val viewModel: WelcomeViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                NavigationEvent.NavigateToLogin -> onNavigateToLogin()
                NavigationEvent.NavigateToMain -> onNavigateToMain()
                NavigationEvent.NavigateToAuthorize -> onNavigateToAuthorize()
            }
        }
    }
    WelcomeView()
}

@Composable
fun WelcomeView(){
    Scaffold(modifier = scaffoldModifier, content = { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Box(Modifier.align(Alignment.BottomCenter).padding(0.dp,100.dp)) {
                HachimiFlag()
            }
        }
    })
}
@Preview
@Composable
fun PreView() {
    PreviewContainer {
        WelcomeView()
    }
}