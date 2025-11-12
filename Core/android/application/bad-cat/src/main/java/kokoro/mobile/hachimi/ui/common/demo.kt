package kokoro.mobile.hachimi.ui.common

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@Composable
fun CircleToRectangleAnimation() {
    // 动画状态：0f = 小圆形，1f = 圆形填满最小边，2f = 矩形
    val animationProgress = remember { Animatable(0f) }

    // 容器尺寸
    val containerSize = 300.dp

    // 记录动画方向
    val isAnimatingToRectangle = remember { mutableStateOf(true) }

    // 协程作用域
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        // 动画画布
        AnimatedShape(
            progress = animationProgress.value,
            containerSize = containerSize,
            modifier = Modifier.fillMaxSize()
        )

        // 控制按钮
        Button(
            onClick = {
                coroutineScope.launch {
                    if (isAnimatingToRectangle.value) {
                        animationProgress.animateTo(
                            targetValue = 2f,
                            animationSpec = tween(
                                durationMillis = 3000,
                                easing = LinearEasing
                            )
                        )
                    } else {
                        animationProgress.animateTo(
                            targetValue = 0f,
                            animationSpec = tween(
                                durationMillis = 3000,
                                easing = LinearEasing
                            )
                        )
                    }
                    isAnimatingToRectangle.value = !isAnimatingToRectangle.value
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text(if (isAnimatingToRectangle.value) "开始动画" else "重置动画")
        }
    }
}

@Composable
fun AnimatedShape(
    progress: Float,
    containerSize: Dp,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current.density
    val initialRadius = 60.dp.value * density / 2
//        with(density) { 60.dp.toPx() } // 初始圆形半径

    Canvas(modifier = modifier) {
        // 容器尺寸转换为像素
        val containerWidth = size.width
        val containerHeight = size.height

        // 计算最小边长度
        val minSide = minOf(containerWidth, containerHeight)

        // 计算当前形状尺寸
        val (currentWidth, currentHeight, cornerRadius) = when {
            progress < 1f -> {
                // 第一阶段：圆形扩大
                val radius = initialRadius + (minSide / 2 - initialRadius) * progress
                val diameter = radius * 2
                Triple(diameter, diameter, radius)
            }
            else -> {
                // 第二阶段：矩形扩张
                val phase = (progress - 1f).coerceIn(0f, 1f)
                val width = minSide + (containerWidth - minSide) * phase
                val height = minSide + (containerHeight - minSide) * phase
                val radius = minSide / 2 * (1 - phase)
                Triple(width, height, radius)
            }
        }

        // 绘制形状
        drawShape(
            width = currentWidth,
            height = currentHeight,
            cornerRadius = cornerRadius,
            containerWidth = containerWidth,
            containerHeight = containerHeight
        )
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawShape(
    width: Float,
    height: Float,
    cornerRadius: Float,
    containerWidth: Float,
    containerHeight: Float
) {
    // 计算中心点
    val centerX = containerWidth / 2
    val centerY = containerHeight / 2

    // 计算左上角位置（居中）
    val left = centerX  - width / 2
    val top = centerY  - height / 2

    // 创建圆角矩形路径
    val path = Path().apply {
        // 左上角弧
        arcTo(
            rect = Rect(
                left = left,
                top = top,
                right = left + 2 * cornerRadius,
                bottom = top + 2 * cornerRadius
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        // 上边线
        lineTo(left + width - cornerRadius, top)

        // 右上角弧
        arcTo(
            rect = Rect(
                left = left + width - 2 * cornerRadius,
                top = top,
                right = left + width,
                bottom = top + 2 * cornerRadius
            ),
            startAngleDegrees = -90f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        // 右边线
        lineTo(left + width, top + height - cornerRadius)

        // 右下角弧
        arcTo(
            rect = Rect(
                left = left + width - 2 * cornerRadius,
                top = top + height - 2 * cornerRadius,
                right = left + width,
                bottom = top + height
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        // 下边线
        lineTo(left + cornerRadius, top + height)

        // 左下角弧
        arcTo(
            rect = Rect(
                left = left,
                top = top + height - 2 * cornerRadius,
                right = left + 2 * cornerRadius,
                bottom = top + height
            ),
            startAngleDegrees = 90f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        // 左边线
        lineTo(left, top + cornerRadius)

        close()
    }

    // 绘制形状轮廓
    drawPath(
        path = path,
        color = Color.Blue,
        style = Stroke(width = 4.dp.toPx())
    )

    // 绘制四个角的标记点（用于可视化）
    drawCornerMarkers(left, top, width, height, cornerRadius)
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawCornerMarkers(
    left: Float,
    top: Float,
    width: Float,
    height: Float,
    cornerRadius: Float
) {
    // 左上角
    drawCircle(
        color = Color.Red,
        center = Offset(left + cornerRadius, top + cornerRadius),
        radius = 5.dp.toPx()
    )

    // 右上角
    drawCircle(
        color = Color.Red,
        center = Offset(left + width - cornerRadius, top + cornerRadius),
        radius = 5.dp.toPx()
    )

    // 右下角
    drawCircle(
        color = Color.Red,
        center = Offset(left + width - cornerRadius, top + height - cornerRadius),
        radius = 5.dp.toPx()
    )

    // 左下角
    drawCircle(
        color = Color.Red,
        center = Offset(left + cornerRadius, top + height - cornerRadius),
        radius = 5.dp.toPx()
    )
}

@Preview
@Composable
fun PreviewCircleToRectangleAnimation() {
    MaterialTheme {
        CircleToRectangleAnimation()
    }
}