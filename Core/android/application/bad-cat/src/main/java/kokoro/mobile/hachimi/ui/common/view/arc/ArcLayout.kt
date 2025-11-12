package kokoro.mobile.hachimi.ui.common.view.arc

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kokoro.mobile.hachimi.common.Modifier.scaffoldModifier
import kokoro.mobile.hachimi.common.clickableWithDebounce
import kokoro.mobile.hachimi.theme.Black
import kokoro.mobile.hachimi.ui.PreviewContainer
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

@Preview
@Composable
fun PreView() {
    PreviewContainer {
        Scaffold(modifier = scaffoldModifier) { innerPadding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)) {
                // 卡片数据
                val cards = listOf("记账", "明细", "规划", "发现", "我的")
                // 圆弧布局容器
                ArcLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                     60.dp, cards, demoCanvas = true
                ){

                }
                OldArcLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    60.dp, cards,
                )
            }
        }
    }
}

@Composable
fun ArcLayout(
    modifier: Modifier = Modifier,
    childSize: Dp,
    childList: List<String>,
    arcRadiusRatio: Float = 1.0f,
    startAngle: Float = 0f, // 起始角度（-90度表示顶部）
    sweepAngle: Float = 90f, // 扫过角度（180度表示半圆）
    demoCanvas:Boolean = false,
    onClick: (Int) -> Unit
) {
    // 计算圆心位置
    val centerX = 0
    val centerY = 0
    var radius = 0F
    var width = 0
    Layout(
        modifier = modifier,
        content = {
            childList.forEachIndexed { index, content ->
                AnimatedCard(content,index,childSize){
                    onClick(index)
                }
            }
        }
    ) { measurables, constraints ->
        val childCount = childList.size
        // 计算布局尺寸
        width = constraints.maxWidth

        // 计算圆弧半径
        radius = width.toFloat() * arcRadiusRatio
        val height = (radius).toInt() // 高度为宽度的一半


        // 测量所有卡片
        val placeables = measurables.map { measurable ->
            measurable.measure(Constraints())
        }


        // 计算卡片位置
        val positions = mutableListOf<Offset>()
        val cardRadii = mutableListOf<Float>()

        // 计算每个卡片的最大半径（从圆心到卡片边缘）
        placeables.forEach { placeable ->
            // 计算卡片对角线的一半作为安全距离
            val cardRadius = sqrt(
                (placeable.width / 2f).pow(2) +
                        (placeable.height / 2f).pow(2)
            )
            cardRadii.add(cardRadius)
        }

        // 找到最大卡片半径
        val maxCardRadius = cardRadii.maxOrNull() ?: 0f

        // 计算有效半径（确保所有卡片都在圆弧内）
        val effectiveRadius = radius - maxCardRadius
        //弧度分为5分 这是其中一份
        val childAngle = sweepAngle  / childCount
        // 计算卡片位置
        for (i in 0 until childCount) {
            // 计算角度（从起始角度开始均匀分布）
            val angle = (startAngle + (sweepAngle * (i+1)) / (childCount)) - childAngle / 2

            val radian = Math.toRadians(angle.toDouble())

            // 计算卡片中心位置（使用有效半径）
            val x = centerX + effectiveRadius * cos(radian).toFloat()
            val y = centerY + effectiveRadius * sin(radian).toFloat()

            positions.add(Offset(x, y))
        }


        // 创建布局
        layout(width, height) {
            // 放置卡片
            placeables.forEachIndexed { index, placeable ->
                val position = positions[index]
                placeable.placeRelative(
                    x = (position.x - placeable.width / 2).toInt(),
                    y = (position.y - placeable.height / 2).toInt()
                )
            }
        }
    }
    if (demoCanvas){
        Canvas(modifier = modifier) {
            drawArc(
                color = Black,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = Offset(0 - radius , 0 - radius),
                size = Size(radius * 2  , radius * 2 ),
                style = Stroke(width = 2.dp.toPx())
            )
        }
    }
}

@Composable
fun OldArcLayout(
    modifier: Modifier = Modifier,
    childSize: Dp,
    childList: List<String>,
    arcRadiusRatio: Float = 0.5f, // 圆弧半径占宽度的比例
    startAngle: Float = 0f, // 起始角度（-90度表示顶部）
    sweepAngle: Float = 90f, // 扫过角度（180度表示半圆）
) {
    val childCount = childList.size
    val density = LocalDensity.current.density
    // 计算圆心位置 分别偏移子View的一半 防止溢出
    val centerX = childSize.value / 2 * density
    val centerY = childSize.value / 2 * density
    var radius = 0F
    Layout(
        modifier = modifier,
        content = {
            if (arcRadiusRatio != 0F){
                childList.forEachIndexed { index, content ->
                    AnimatedCard(content,index,childSize){

                    }
                }
            }
        }
    ) { measurables, constraints ->
        // 计算布局尺寸
        val width = constraints.maxWidth


        // 计算圆弧半径
        radius = width.toFloat() * arcRadiusRatio
        val height = (centerX*2 + radius).toInt() // 高度为宽度的一半


        // 测量所有卡片
        val placeables = measurables.map { measurable ->
            measurable.measure(Constraints())
        }


        // 计算卡片位置
        val positions = mutableListOf<Offset>()

        for (i in 0 until childCount) {
            // 计算角度（从起始角度开始均匀分布）
            val angle = (startAngle + (sweepAngle * i) / (childCount - 1))

            val radian = Math.toRadians(angle.toDouble())

            // 计算卡片位置
            val x = centerX + radius * cos(radian).toFloat()
            val y = centerY + radius * sin(radian).toFloat()
            positions.add(Offset(x, y))
        }

        // 创建布局
        layout(width, height) {
            // 放置卡片
            placeables.forEachIndexed { index, placeable ->
                val position = positions[index]
                placeable.placeRelative(
                    x = (position.x - placeable.width / 2).toInt(),
                    y = (position.y - placeable.height / 2).toInt()
                )
            }
        }
    }
    Canvas(modifier = modifier) {
        drawArc(
            color = Black,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = true,
            topLeft = Offset(centerX - radius , centerX  - radius),
            size = Size(radius * 2  , radius * 2 ),
            style = Stroke(width = 2.dp.toPx())
        )
    }
}



@Composable
fun AnimatedCard(content: String, index: Int,childSize: Dp,onClick:(()-> Unit)) {
    val infiniteTransition = rememberInfiniteTransition()
    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Card(
        shape = RoundedCornerShape(90.dp),
        modifier = Modifier
            .size(childSize)
            .clickableWithDebounce { onClick() }
            .scale(pulse)
//            .animatePlacement()

    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}