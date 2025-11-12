package kokoro.mobile.template.ui.features.main.bottom_layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import kokoro.mobile.template.common.nonScaledSp
import kokoro.mobile.template.ui.common.view.PreviewData
import kokoro.mobile.template.theme.AppColors
import kokoro.mobile.template.theme.Color_77838f
import kokoro.mobile.template.theme.KokoroTheme
import kokoro.mobile.template.theme.MainThemeColor


/**
 * Created by xianjie on 2024年12月30日14:10:12
 *
 * Description:
 */

/**
 * 预览
 */
@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    _root_ide_package_.kokoro.mobile.template.theme.KokoroTheme {
        BottomBar(_root_ide_package_.kokoro.mobile.template.ui.common.view.PreviewData.tabState, {})
    }
}

@Composable
fun BottomBar(
    state: BottomLayoutState,
    onEvent: (BottomLayoutEvent) -> Unit,
    selectedTextColor: Color = _root_ide_package_.kokoro.mobile.template.theme.MainThemeColor,
    unselectedTextColor: Color = _root_ide_package_.kokoro.mobile.template.theme.Color_77838f,
    fontSize: TextUnit = 12.nonScaledSp,
) {
    require(state.items.isNotEmpty()) { "BottomBarDataList must not be empty." }
    Row(
        Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                clip = true
            )
            .clip(
                RoundedCornerShape(
                    topStart = 18.dp,
                    topEnd = 18.dp,
                )
            )
            .background(_root_ide_package_.kokoro.mobile.template.theme.AppColors.localColors.current.mainColor),
        horizontalArrangement = Arrangement.Center
    ) {
        state.items.forEach { (key, value) ->
            BottomBarItem(
                key to value,
                Modifier.weight(1f),
                state.selectedKey,
                selectedTextColor,
                unselectedTextColor,
                fontSize
            ) {currentKey->
                onEvent.invoke(BottomLayoutEvent.OnTabSelected(currentKey))
            }
        }
    }
}

@Composable
fun BottomBarItem(
    data: Pair<Int, BottomLayoutData>,
    modifier: Modifier,
    selectKey: Int,
    selectedTextColor: Color,
    unselectedTextColor: Color,
    fontSize: TextUnit,
    onSelectedChanged: (currentKey: Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable { onSelectedChanged.invoke(data.first) }
            .padding(0.dp, 12.dp)
    ) {
        Icon(
            modifier = Modifier.size(data.second.width.dp, data.second.height.dp),
            painter = painterResource(id = data.second.icon),
            contentDescription = data.second.content,
            tint = if (selectKey == data.first) selectedTextColor else unselectedTextColor
        )
        Text(
            data.second.content,
            color = if (selectKey == data.first) selectedTextColor else unselectedTextColor,
            fontSize = fontSize
        )
    }
}