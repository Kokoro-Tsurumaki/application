package kokoro.mobile.hachimi.ui.common.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kokoro.mobile.hachimi.common.clickableWithDebounce
import kokoro.mobile.hachimi.theme.MainThemeColor

/**
 * Created by xianjie on 2025年1月22日11:00:42
 *
 * Description:
 */
@Composable
fun CustomCheckBox(checked:Boolean,checkedCallback:()->Unit){
    val color = MaterialTheme.colorScheme.onPrimaryContainer
    Box(
        modifier = Modifier
            .size(14.dp)
            .clickableWithDebounce { checkedCallback.invoke() }
            .background(
                color = if (checked) color else Color.White,
                shape = RoundedCornerShape(90.dp)
            )
            .border(
                width = 1.dp,
                color = if (checked) color else Color.Gray,
                shape = RoundedCornerShape(90.dp)
            ),

        ){
        if (checked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}