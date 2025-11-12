package kokoro.mobile.template.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

/**
 * Created by xianjie on 2025年1月3日16:37:44
 *
 * Description:
 */

//不随设置改变大小的sp
@Stable
val Int.nonScaledSp: TextUnit
    @Composable
    get() = with(LocalDensity.current){
    this@nonScaledSp.dp.toSp()
}

/**
 * 防抖点击
 */
fun Modifier.clickableWithDebounce(
    debounceInterval: Long = 500L,
    onClick: () -> Unit
) = composed {
    var lastClickTime by remember { mutableLongStateOf(0L) }

    Modifier.clickable(indication = null, interactionSource = null) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > debounceInterval) {
            lastClickTime = currentTime
            onClick()
        }
    }
}


/**
 * 扩大点击范围
 */
@Composable
fun ViewWithExtent(expandBy: PaddingValues, view:@Composable ()->Unit,
                    onClick:  () -> Unit){
    Box(
        modifier = Modifier
            .clickableWithDebounce(onClick = onClick)
            .padding(expandBy)
            .wrapContentSize()
    ) {
        view.invoke()
    }
}

/**
 *
 */










