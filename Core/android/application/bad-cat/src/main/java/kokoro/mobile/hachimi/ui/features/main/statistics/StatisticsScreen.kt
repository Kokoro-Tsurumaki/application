package kokoro.mobile.hachimi.ui.features.main.statistics

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import kokoro.mobile.hachimi.R
import kokoro.mobile.hachimi.common.Modifier.scaffoldModifier
import kokoro.mobile.hachimi.common.nonScaledSp
import kokoro.mobile.hachimi.ui.PreviewContainer
import kokoro.mobile.hachimi.ui.common.Category
import kokoro.mobile.hachimi.ui.common.HorizontalNestedList
import kokoro.mobile.hachimi.ui.common.SubItem
import kokoro.mobile.hachimi.ui.common.base.VMStateContainer

/**
 * =================================================================================================
 * FILE GENERATED AUTOMATICALLY BY GRADLE TASK
 * Generated on: Tue Sep 23 16:55:16 CST 2025
 * Command:`./gradlew createTemplateForMVI -Ptflag=Statistics -Ptpackage=kokoro.mobile.hachimi -Ptpath=application/bad-cat/src/main/java/kokoro/mobile/hachimi/ui/features`
 * Module: Statistics
 * Package: kokoro.mobile.hachimi
 * =================================================================================================
 */

@Composable
fun StatisticsScreen() {
    VMStateContainer<StatisticsViewModel>(onInit = { viewModel ->

    }, effectCallBack = {

    }) {
        val viewModel: StatisticsViewModel = viewModel()
        val uiState by viewModel.uiFlow.collectAsStateWithLifecycle()
        StatisticsView(uiState) {
            viewModel.sendUIAction(it)
        }
    }
}

@Composable
fun StatisticsView(
    state: StatisticsContract.StatisticsState,
    onAction: ((StatisticsContract.StatisticsAction) -> Unit)
) {
    // 根据状态显示不同内容
    when {
        state.isLoading -> LoadingState()
        state.error != null -> ErrorState(state.error, onAction)
        else -> StatisticsContent(state, onAction)
    }
}

@Composable
private fun StatisticsContent(
    state: StatisticsContract.StatisticsState,
    onAction: ((StatisticsContract.StatisticsAction) -> Unit)
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Spacer(Modifier
            .height(40.dp)
            .wrapContentWidth())

        // 标题和时间选择器
        HeaderSection(state, onAction)

        Spacer(modifier = Modifier.height(24.dp))

        // 关键指标卡片
        KeyMetricsSection(state)

        val categories = listOf(
            Category(
                id = 1,
                title = "设计资源",
                color = Color(0xFF6A1B9A),
                subItems = listOf(
                    SubItem(1, "UI Kits", "精选的用户界面套件集合"),
                    SubItem(2, "图标集", "各种风格的图标资源"),
                    SubItem(3, "字体库", "精选的免费和付费字体"),
                    SubItem(4, "配色方案", "设计师精选的配色方案")
                )
            ),
            Category(
                id = 2,
                title = "开发工具",
                color = Color(0xFF0277BD),
                subItems = listOf(
                    SubItem(5, "代码编辑器", "高效的代码编辑工具"),
                    SubItem(6, "调试工具", "强大的调试和测试工具"),
                    SubItem(7, "版本控制", "Git和其他版本控制系统"),
                    SubItem(8, "API测试", "REST和GraphQL测试工具")
                )
            ),
            Category(
                id = 3,
                title = "学习资源",
                color = Color(0xFF2E7D32),
                subItems = listOf(
                    SubItem(9, "在线课程", "精选的编程和设计课程"),
                    SubItem(10, "文档", "官方文档和教程"),
                    SubItem(11, "社区", "开发者交流社区"),
                    SubItem(12, "书籍", "推荐的编程书籍")
                )
            ),
            Category(
                id = 4,
                title = "模板",
                color = Color(0xFFEF6C00),
                subItems = listOf(
                    SubItem(13, "网站模板", "响应式网站模板"),
                    SubItem(14, "应用模板", "移动应用UI模板"),
                    SubItem(15, "演示文稿", "精美的演示文稿模板"),
                    SubItem(16, "简历模板", "专业的简历设计模板")
                )
            )
        )

        HorizontalNestedList(
            categories = categories,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )

//        Spacer(modifier = Modifier.height(24.dp))
//
//        // 图表区域
//        ChartsSection(state)
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // 详细数据表格
//        DetailedDataSection(state)
    }
}

@Composable
private fun HeaderSection(
    state: StatisticsContract.StatisticsState,
    onAction: ((StatisticsContract.StatisticsAction) -> Unit)
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "统计概览",
            fontSize = 24.nonScaledSp,
            fontWeight = FontWeight.Bold
        )

        // 时间选择器
        TimeRangeSelector(state.selectedTimeRange, onAction)
    }
}

@Composable
private fun TimeRangeSelector(
    selectedRange: String,
    onAction: ((StatisticsContract.StatisticsAction) -> Unit)
) {
    val timeRanges = listOf("本月", "年度")

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray.copy(alpha = 0.2f))
            .padding(4.dp)
    ) {
        timeRanges.forEach { range ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (range == selectedRange) MaterialTheme.colorScheme.primary else Color.Transparent)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .clickable { onAction(StatisticsContract.StatisticsAction.SelectTimeRange(range)) }
            ) {
                Text(
                    text = range,
                    color = if (range == selectedRange) Color.White else MaterialTheme.colorScheme.onBackground,
                    fontSize = 14.nonScaledSp
                )
            }
        }
    }
}

@Composable
private fun KeyMetricsSection(state: StatisticsContract.StatisticsState) {

    Column(modifier = Modifier.fillMaxWidth(),) {
        // 示例关键指标卡片
        MetricCard(
            Modifier.fillMaxWidth(),
            title = "总支出",
            value = state.totalOut,
            icon = R.drawable.ic_launcher, // 假设有这些图标资源
            color = Color(0xFF4CAF50)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {


            MetricCard(
                Modifier.weight(1F),
                title = "总收入",
                value = state.totalIn,
                icon = R.drawable.ic_launcher,
                color = Color(0xFF2196F3)
            )

            Spacer(modifier = Modifier.width(16.dp))

            MetricCard(
                Modifier.weight(1F),
                title = "结余",
                value = state.surplus,
                icon = R.drawable.ic_launcher,
                color = Color(0xFFFF9800)
            )
        }
    }

}

@Composable
private fun MetricCard(modifier: Modifier, title: String, value: String, icon: Int, color: Color) {
    Card(
        modifier = modifier.wrapContentHeight()
            ,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 14.nonScaledSp,
                    color = Color.Gray
                )

                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = title,
                    modifier = Modifier.size(24.dp),
                    tint = color
                )
            }

            Text(
                text = value,
                fontSize = 24.nonScaledSp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ChartsSection(state: StatisticsContract.StatisticsState) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "收支趋势",
            fontSize = 18.nonScaledSp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 简单的柱状图模拟
        BarChart(state)

        Spacer(modifier = Modifier.height(24.dp))

        // 饼图模拟
        Text(
            text = "流量来源",
            fontSize = 18.nonScaledSp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        PieChart(state.trafficSourceData)
    }
}

@Composable
private fun BarChart(state: StatisticsContract.StatisticsState) {
    if (state.dayOfMonthOut.isEmpty() && state.dayOfMonthIn.isEmpty()){
        return
    }
    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        data = remember {
            listOf(
                Line(
                    label = "支出",
                    values = state.dayOfMonthOut,
                    color = SolidColor(Color(0xFF2BC0A1)),
                    curvedEdges = false,

                    firstGradientFillColor = Color(0xFF2BC0A1).copy(alpha = .5f),
                    secondGradientFillColor = Color.Transparent,
                    strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                    gradientAnimationDelay = 1000,
                    drawStyle = DrawStyle.Stroke(width = 2.dp),

                    dotProperties = DotProperties(
                        enabled = true,
                        color = SolidColor(Color.White),
                        strokeWidth = 1.dp,
                        radius = 2.dp,
                        strokeColor = SolidColor(Color(0xFF2BC0A1)),
                    )
                ),
                Line(
                    label = "收入",
                    values = state.dayOfMonthIn,
                    color = SolidColor(Color.Cyan),
                    curvedEdges = false,

                    firstGradientFillColor = Color.Cyan.copy(alpha = .5f),
                    secondGradientFillColor = Color.Transparent,
                    strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                    gradientAnimationDelay = 1000,
                    drawStyle = DrawStyle.Stroke(width = 2.dp),

                    dotProperties = DotProperties(
                        enabled = true,
                        color = SolidColor(Color.White),
                        strokeWidth = 1.dp,
                        radius = 2.dp,
                        strokeColor = SolidColor(Color.Cyan),
                    )
                ),
            )
        },
        curvedEdges = false,
        animationMode = AnimationMode.Together(delayBuilder = {
            it * 500L
        }),
//        labelProperties = LabelProperties(true,labels = state.currentDayLengthOfMonthFormat)
    )
}

@Composable
private fun PieChart(data: List<StatisticsContract.TrafficSource>) {
    val total = data.sumOf { it.percentage }
    var startAngle = 0f

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // 饼图模拟
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray.copy(alpha = 0.1f))
        ) {
            // 这里应该使用Canvas绘制饼图，但为了简化使用Box模拟
            Text(
                text = "饼图预览",
                modifier = Modifier.align(Alignment.Center),
                color = Color.Gray
            )
        }

        // 图例
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            data.forEach { source ->
                LegendItem(source.name, source.percentage, source.color)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun LegendItem(name: String, value: Int, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "$name: $value%",
            fontSize = 14.nonScaledSp
        )
    }
}

@Composable
private fun DetailedDataSection(state: StatisticsContract.StatisticsState) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "详细数据",
            fontSize = 18.nonScaledSp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 数据表格
        DataTable(state.detailedData)
    }
}

@Composable
private fun DataTable(data: List<StatisticsContract.DetailedData>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray.copy(alpha = 0.1f))
    ) {
        // 表头
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("页面", fontWeight = FontWeight.Bold)
            Text("访问量", fontWeight = FontWeight.Bold)
            Text("跳出率", fontWeight = FontWeight.Bold)
            Text("转化率", fontWeight = FontWeight.Bold)
        }

        // 数据行
        data.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(item.page)
                Text(item.visits.toString())
                Text("${item.bounceRate}%")
                Text("${item.conversionRate}%")
            }

            // 分隔线
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.LightGray.copy(alpha = 0.2f))
            )
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorState(error: String, onAction: ((StatisticsContract.StatisticsAction) -> Unit)) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "加载统计数据时出错",
            fontSize = 18.nonScaledSp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = error,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 重试按钮
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primary)
                .clickable { onAction(StatisticsContract.StatisticsAction.Reload) }
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Text(
                text = "重试",
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun PreView() {
    PreviewContainer {
        Scaffold(modifier = scaffoldModifier) { innerPadding ->
            StatisticsView(StatisticsContract.StatisticsState()) {

            }
        }
    }
}