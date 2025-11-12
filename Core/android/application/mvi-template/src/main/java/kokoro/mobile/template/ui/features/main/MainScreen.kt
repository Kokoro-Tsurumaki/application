package kokoro.mobile.template.ui.features.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kokoro.mobile.template.Constant
import kokoro.mobile.template.ui.features.main.bottom_layout.BottomBar
import kokoro.mobile.template.ui.features.main.home.HomeScreen

/**
 * Created by xianjie on 2025年1月3日21:50:52
 *
 * Description:
 */

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()){
    val state by viewModel.tabState.collectAsStateWithLifecycle()
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .navigationBarsPadding(), bottomBar = {
        _root_ide_package_.kokoro.mobile.template.ui.features.main.bottom_layout.BottomBar(
            state,
            viewModel::handleEvent
        )
    }) { innerPadding ->
        when(state.selectedKey){
            _root_ide_package_.kokoro.mobile.template.Constant.HOME_TYPE_RUNNING -> {
                _root_ide_package_.kokoro.mobile.template.ui.features.main.home.HomeScreen(
                    innerPadding
                )
            }
            _root_ide_package_.kokoro.mobile.template.Constant.HOME_TYPE_SETTING -> {}
            _root_ide_package_.kokoro.mobile.template.Constant.HOME_TYPE_DETAIL -> {}
            _root_ide_package_.kokoro.mobile.template.Constant.HOME_TYPE_INFO -> {}
        }
    }
}