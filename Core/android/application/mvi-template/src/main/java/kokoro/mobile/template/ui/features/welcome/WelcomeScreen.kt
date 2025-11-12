package kokoro.mobile.template.ui.features.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kokoro.mobile.template.R

/**
 * Created by xianjie on 2025年1月7日14:00:58
 *
 * Description:
 */
@Composable
fun WelcomeScreen(viewModel: WelcomeViewModel = hiltViewModel(),
                  onNavigateToLogin: () -> Unit,
                  onNavigateToMain: () -> Unit){
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                NavigationEvent.NavigateToLogin -> onNavigateToLogin()
                NavigationEvent.NavigateToMain -> onNavigateToMain()
            }
        }
    }
    Box (modifier = Modifier.fillMaxSize()){
        Box(modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 200.dp)){
            Image(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.icon_welcome),
                contentDescription = "雨昕智慧",
            )
        }
    }
}