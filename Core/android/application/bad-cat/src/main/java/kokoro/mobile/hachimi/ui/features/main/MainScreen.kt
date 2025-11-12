package kokoro.mobile.hachimi.ui.features.main

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kokoro.mobile.hachimi.common.Modifier.scaffoldModifier
import kokoro.mobile.hachimi.common.clickableWithDebounce
import kokoro.mobile.hachimi.ui.PreviewContainer
import kokoro.mobile.hachimi.ui.common.view.arc.ArcLayout
import kokoro.mobile.hachimi.ui.features.main.home.HomeScreen
import kokoro.mobile.hachimi.ui.features.main.mine.MineScreen
import kokoro.mobile.hachimi.ui.features.main.statistics.StatisticsScreen
import timber.log.Timber

/**
 * Created by xianjie on 2025年1月3日21:50:52
 *
 * Description:
 */

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val state by viewModel.tabState.collectAsStateWithLifecycle()
    MainView(state){
        viewModel.handleEvent(it)
    }
}

@Composable
fun MainView(uiState: MainTabState,onAction:((MainTabEvent)->Unit)) {
    Scaffold(modifier = scaffoldModifier) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            when (uiState.selectedKey) {
                0 -> {
                    HomeScreen(innerPadding)
                }

                1 -> {
                    StatisticsScreen()
                }

                4 ->{
                    MineScreen()
                }
            }

            var show by remember { mutableStateOf(false) }

            val animateFloat by animateFloatAsState(
                targetValue = if (show) {
                    1.0F
                } else {
                    0F
                },
            )

            val animateSweepFloat by animateFloatAsState(
                targetValue = if (show) {
                    90F
                } else {
                    0F
                },
            )

            val animateButtonFloat by animateFloatAsState(
                targetValue = if (show) {
                    1.0F
                } else {
                    0.2F
                },
            )
            val color = MaterialTheme.colorScheme.surfaceVariant
            Canvas(
                modifier = Modifier
                    .size(300.dp * animateButtonFloat)
                    .clickableWithDebounce(
                        onClick = {
                            Timber.e("test")
                            show = !show
                        }
                    )) {
                drawArc(
                    color = color.copy(1.4F - animateButtonFloat),
                    startAngle = 0F,
                    sweepAngle = 90F,
                    useCenter = true,
                    topLeft = Offset(0 - size.width, 0 - size.width),
                    size = Size(size.width * 2, size.width * 2),
                    style = Fill
                )
            }

            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "加",
                modifier = Modifier.size(28.dp)
            )


            // 圆弧布局容器
//            AnimatedVisibility(animateFloat > 0f) {
            ArcLayout(
                modifier = Modifier
                    .width(300.dp)
                    .wrapContentHeight()
                    .clipToBounds(), 60.dp, uiState.items,
                startAngle = 0F, sweepAngle = animateSweepFloat, arcRadiusRatio = animateFloat
            ) {
                show = !show
                onAction.invoke(MainTabEvent.OnTabSelected(it))
            }
//            }
        }
    }
}

@Preview
@Composable
fun PreView() {
    PreviewContainer {
        MainView(MainTabState()){

        }
    }
}




