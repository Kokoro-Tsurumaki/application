package kokoro.mobile.template.ui.features.web

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kokoro.mobile.template.ui.common.base.ModernWebView
import kokoro.mobile.template.ui.common.toolbar.Toolbar

/**
 * Created by xianjie on 2025年1月21日10:07:06
 *
 * Description:
 */
@Composable
fun WebScreen(url: String, titleName: String) {
    val isLoading by remember { mutableStateOf(true) }
    val error by remember { mutableStateOf<String?>(null) }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {

            _root_ide_package_.kokoro.mobile.template.ui.common.toolbar.Toolbar(titleName)

            _root_ide_package_.kokoro.mobile.template.ui.common.base.ModernWebView(
                url = url,
                modifier = Modifier.fillMaxSize()
            )

            // 加载状态
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            // 错误状态
            error?.let { errorMessage ->
                Text(
                    text = errorMessage,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
            }
        }
    }
}