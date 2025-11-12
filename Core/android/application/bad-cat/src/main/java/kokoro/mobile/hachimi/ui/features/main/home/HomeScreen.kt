package kokoro.mobile.hachimi.ui.features.main.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kokoro.mobile.hachimi.common.Modifier.scaffoldModifier
import kokoro.mobile.hachimi.database.CategoriesChild
import kokoro.mobile.hachimi.database.CategoriesParent
import kokoro.mobile.hachimi.ui.PreviewContainer
import kokoro.mobile.hachimi.ui.common.CircleToRectangleAnimation
import kokoro.mobile.hachimi.ui.common.base.VMStateContainer
import kokoro.mobile.hachimi.ui.common.icon.Minus
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Created by xianjie on 2025年1月3日21:31:10
 *
 * Description:
 */
@Composable
fun HomeScreen(innerPadding: PaddingValues) {
    VMStateContainer<HomeViewModel>(onInit = {

    }, effectCallBack = {

    }) {
        val viewModel: HomeViewModel = viewModel()
        val uiState by viewModel.uiFlow.collectAsStateWithLifecycle()
        HomeView(uiState){
            viewModel.onUIAction(it)
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(uiState: HomeContract.HomeState, onAction:((HomeContract.HomeAction)-> Unit)) {
    // 键盘按键定义
    val keyboardKeys = listOf(
        "7", "8", "9", uiState.currentInputType,
        "4", "5", "6", "+",
        "1", "2", "3", "-",
        ".", "0", "⌫", "完成" // 删除键

    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 16.dp, 16.dp, 0.dp)
    ) {

        // 顶部标题
        Text(
            text = "记账",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = 24.dp)
        )

        // 分类选择器
        CategorySelector(
            Modifier.weight(1F),
            selectedCategory = uiState.selectedCategory, childCategories = uiState.childCategories,
            categories = uiState.parentCategories,
            onCategorySelected = { onAction(HomeContract.HomeAction.UpdateSelectedCategory(it)) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 时间选择器
        DateSelector(
            selectedDate = uiState.selectedDate,
            onShowDatePicker = { onAction(HomeContract.HomeAction.ShowDatePicker(it)) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 输入显示区域
        InputDisplay(
            value = uiState.inputValue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        )


        // 自定义数字键盘
        NumberKeyboard(
            keys = keyboardKeys,
            onKeyPressed = { key ->
                when (key) {
                    "支出" -> {
                        onAction(HomeContract.HomeAction.UpdateInputType("收入"))
                    }

                    "收入" -> {
                        onAction(HomeContract.HomeAction.UpdateInputType("支出"))
                    }
                    "⌫" -> {
                        onAction(HomeContract.HomeAction.NumberKeyboardDelete())
                    }

                    "完成" -> {
                        onAction(HomeContract.HomeAction.NumberKeyboardFinish())
                    }

                    "." -> {
                        onAction(HomeContract.HomeAction.NumberKeyboardDecimal())
                    }

                    else -> {
                        onAction(HomeContract.HomeAction.NumberKeyboardNumber(key))
                    }
                }
            }
        )
    }

    // 日期选择器对话框
    if (uiState.showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = uiState.selectedDate
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        )
        DatePickerDialog(
            onDismissRequest = { onAction(HomeContract.HomeAction.ShowDatePicker(false)) },
            confirmButton = {
                TextButton(onClick = {
                    onAction(HomeContract.HomeAction.UpdateSelectedDate(
                        Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    ))
                    onAction(HomeContract.HomeAction.ShowDatePicker(false))
                }) {
                    Text("确定")
                }
            },
            dismissButton = {
                TextButton(onClick = { onAction(HomeContract.HomeAction.ShowDatePicker(false)) }) {
                    Text("取消")
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
}

// 分类选择器组件
@Composable
fun CategorySelector(
    modifier: Modifier,
    selectedCategory: String,
    categories: List<CategoriesParent>,
    childCategories: List<CategoriesChild>,
    onCategorySelected: (String) -> Unit
) {
    Column(modifier) {


        Text(
            text = "分类",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        var contentWidth by remember { mutableStateOf(0) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .horizontalScroll(ScrollState(0)),
        ) {
            // 使用 Layout 测量内容宽度
            Layout(
                content = {
                    // 分类行
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(18.dp),
                    ) {
                        categories.forEach { category ->
                            Text(
                                text = category.content,
                            )
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    // 进度条 - 使用测量到的宽度
                    LinearProgressIndicator(
                        progress = {0.2f},
                        modifier = Modifier
                            .width(contentWidth.dp) // 使用测量宽度
                            .height(4.dp),
                    )
                },
                modifier = Modifier.padding(vertical = 8.dp)
            ) { measurables, constraints ->
                // 测量 Row
                val rowPlaceable = measurables[0].measure(constraints.copy(maxWidth = Constraints.Infinity))
                val spacer = measurables[1].measure(constraints.copy())
                // 测量进度条
                val progressPlaceable = measurables[2].measure(constraints.copy(maxWidth = rowPlaceable.width))

                // 更新内容宽度
                contentWidth = rowPlaceable.width

                layout(rowPlaceable.width, rowPlaceable.height + progressPlaceable.height) {
                    // 放置 Row
                    rowPlaceable.place(0, 0)
                    spacer.place(0, rowPlaceable.height)

                    // 放置进度条
                    progressPlaceable.place(0, rowPlaceable.height+spacer.height)
                }
            }

        }


        Spacer(modifier = Modifier.height(8.dp))

//        Box(modifier = Modifier.fillMaxWidth().wrapContentHeight().horizontalScroll(ScrollState(0))) {
//            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                categories.forEach { category ->
//                    CategoryChip(
//                        category = category.title,
//                        isSelected = category.title == selectedCategory,
//                        onClick = { onCategorySelected(category.title) }
//                    )
//                }
//            }
//        }

        LazyHorizontalStaggeredGrid(
            rows = StaggeredGridCells.Fixed(3),
            horizontalItemSpacing = 4.dp,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            content = {
                items(childCategories) { category ->
                    CategoryChip(
                        category = category.content,
                        isSelected = category.content == selectedCategory,
                        onClick = { onCategorySelected(category.content) }
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )

//        FlowRow(
//            modifier = Modifier.fillMaxWidth(),
////            horizontalArrangement = Arrangement.spacedBy(8.dp),
////            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            childCategories.forEach { category ->
//                CategoryChip(
//                    category = category.name,
//                    isSelected = category.name == selectedCategory,
//                    onClick = { onCategorySelected(category.name) }
//                )
//            }
//        }
    }
}

// 分类标签组件
@Composable
fun CategoryChip(
    category: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Surface(
        color = backgroundColor,
        border = BorderStroke(
            width = 1.dp,
            color = if (!isSelected) MaterialTheme.colorScheme.outline else Color.Transparent
        ),
        onClick = onClick,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = category,
            color = contentColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 0.dp)
                .wrapContentHeight()
        )
    }
}

// 时间选择器组件
@Composable
fun DateSelector(
    selectedDate: LocalDate,
    onShowDatePicker: (Boolean) -> Unit
) {
    Column {
        Text(
            text = "时间",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onShowDatePicker(true) }
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "选择日期",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = selectedDate.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "展开",
                modifier = Modifier.size(24.dp)
            )
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
        )
    }
}

// 输入显示组件
@Composable
fun InputDisplay(value: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.align(Alignment.CenterEnd),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// 数字键盘组件
@Composable
fun NumberKeyboard(
    keys: List<String>,
    onKeyPressed: (String) -> Unit
) {
    val keyColors = mapOf(
        "收入" to MaterialTheme.colorScheme.tertiary,
        "支出" to MaterialTheme.colorScheme.error,
        "⌫" to MaterialTheme.colorScheme.error,
        "完成" to MaterialTheme.colorScheme.primary
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(keys) { key ->
            val backgroundColor = keyColors[key] ?: MaterialTheme.colorScheme.surface
            val contentColor = if (keyColors.containsKey(key)) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onSurface
            }

            KeyButton(
                key = key,
                backgroundColor = backgroundColor,
                contentColor = contentColor,
                onClick = { onKeyPressed(key) }
            )
        }
    }
}

// 键盘按键组件
@Composable
fun KeyButton(
    key: String,
    backgroundColor: Color,
    contentColor: Color,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .aspectRatio(1f)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
    ) {
        when (key) {
            "+" -> Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "加",
                tint = contentColor,
                modifier = Modifier.size(28.dp)
            )

            "-" -> Icon(
                imageVector = Icons.Default.Minus,
                contentDescription = "减",
                tint = contentColor,
                modifier = Modifier.size(28.dp)
            )


            "⌫" -> Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "删除",
                tint = contentColor,
                modifier = Modifier.size(28.dp)
            )

            else -> Text(
                text = key,
                style = MaterialTheme.typography.titleLarge,
                color = contentColor
            )
        }
    }
}

@Preview
@Composable
fun PreView() {
    PreviewContainer {
        Scaffold(modifier = scaffoldModifier) { innerPadding ->
            HomeView(HomeContract.HomeState()){

            }
        }
    }
}

