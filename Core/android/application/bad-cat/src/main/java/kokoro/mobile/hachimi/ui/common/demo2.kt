package kokoro.mobile.hachimi.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlin.math.absoluteValue

// 数据模型
data class Category(
    val id: Int,
    val title: String,
    val color: Color,
    val subItems: List<SubItem>
)

data class SubItem(
    val id: Int,
    val title: String,
    val description: String,
    val icon: Int? = null
)

// 主组合函数
@Composable
fun HorizontalNestedList(
    categories: List<Category>,
    modifier: Modifier = Modifier,
    onSubItemClick: (SubItem) -> Unit = {}
) {
    // 记录当前选中的一级分类索引
    var selectedCategoryIndex by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 一级分类标题 - 横向滑动
        CategoryTabs(
            categories = categories,
            selectedIndex = selectedCategoryIndex,
            onCategorySelected = { index -> selectedCategoryIndex = index },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 二级内容 - 横向滑动卡片
        SubItemsCarousel(
            subItems = categories[selectedCategoryIndex].subItems,
            categoryColor = categories[selectedCategoryIndex].color,
            onItemClick = onSubItemClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        )
    }
}

// 一级分类标签栏
@Composable
fun CategoryTabs(
    categories: List<Category>,
    selectedIndex: Int,
    onCategorySelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()

    Box(modifier = modifier) {
        // 背景装饰
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
                        )
                    )
                )
        )

        LazyRow(
            state = scrollState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(categories) { index, category ->
                CategoryTabItem(
                    category = category,
                    isSelected = index == selectedIndex,
                    onClick = { onCategorySelected(index) }
                )
            }
        }

        // 左右滚动指示器
        ScrollIndicators(scrollState, categories.size)
    }
}

// 一级分类标签项
@Composable
fun CategoryTabItem(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (isSelected) category.color.copy(alpha = 0.2f) else Color.Transparent
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = category.title,
            color = if (isSelected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 16.sp
        )
    }
}

// 二级内容轮播
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubItemsCarousel(
    subItems: List<SubItem>,
    categoryColor: Color,
    onItemClick: (SubItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()
    val centerOffset = remember { mutableStateOf(0f) }

    Box(modifier = modifier) {
        LazyRow(
            state = scrollState,
            contentPadding = PaddingValues(horizontal = 80.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(subItems) { index, item ->
                val scale = calculateScale(scrollState, index)
                val offset = calculateOffset(scrollState, index)

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(240.dp)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            translationX = offset
                        }
                        .zIndex(scale) // 确保放大项在最上层
                ) {
                    SubItemCard(
                        item = item,
                        categoryColor = categoryColor,
                        onClick = { onItemClick(item) },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        // 左右滚动指示器
        ScrollIndicators(scrollState, subItems.size)
    }
}

// 计算卡片缩放比例
private fun calculateScale(scrollState: androidx.compose.foundation.lazy.LazyListState, index: Int): Float {
    val firstVisible = scrollState.firstVisibleItemIndex
    val firstVisibleOffset = scrollState.firstVisibleItemScrollOffset

    // 计算当前项在屏幕中的位置
    val position = if (index == firstVisible) {
        -firstVisibleOffset / 1000f
    } else if (index == firstVisible + 1) {
        1 - firstVisibleOffset / 1000f
    } else {
        0f
    }

    // 缩放比例：中心项最大，两侧逐渐缩小
    return 1f - 0.1f * position.absoluteValue
}

// 计算卡片偏移量
private fun calculateOffset(scrollState: androidx.compose.foundation.lazy.LazyListState, index: Int): Float {
    val firstVisible = scrollState.firstVisibleItemIndex
    if (index < firstVisible || index > firstVisible + 1) return 0f

    val firstVisibleOffset = scrollState.firstVisibleItemScrollOffset
    return if (index == firstVisible) {
        -firstVisibleOffset.toFloat()
    } else {
        0f
    }
}

// 二级内容卡片
@Composable
fun SubItemCard(
    item: SubItem,
    categoryColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            categoryColor.copy(alpha = 0.1f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // 标题
                Text(
                    text = item.title,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 描述
                Text(
                    text = item.description,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.weight(1f))

                // 底部装饰
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(categoryColor.copy(alpha = 0.5f))
                )
            }
        }
    }
}

// 滚动指示器
@Composable
fun ScrollIndicators(
    scrollState: androidx.compose.foundation.lazy.LazyListState,
    itemCount: Int,
    modifier: Modifier = Modifier
) {
    val showLeftIndicator = scrollState.firstVisibleItemIndex > 0
    val showRightIndicator = scrollState.firstVisibleItemIndex < itemCount - 1

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 左侧指示器
        AnimatedVisibility(
            visible = showLeftIndicator,
            enter = fadeIn(animationSpec = spring(stiffness = Spring.StiffnessLow)),
            exit = fadeOut(animationSpec = spring(stiffness = Spring.StiffnessLow)),
            modifier = Modifier.zIndex(1f)
        ) {
            IconButton(
                onClick = { /* TODO: 滚动到上一个 */ },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Scroll left",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        // 右侧指示器
        AnimatedVisibility(
            visible = showRightIndicator,
            enter = fadeIn(animationSpec = spring(stiffness = Spring.StiffnessLow)),
            exit = fadeOut(animationSpec = spring(stiffness = Spring.StiffnessLow)),
            modifier = Modifier.zIndex(1f)
        ) {
            IconButton(
                onClick = { /* TODO: 滚动到下一个 */ },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Scroll right",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

// 预览函数
@Preview
@Composable
fun HorizontalNestedListPreview() {
    MaterialTheme {
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
    }
}

