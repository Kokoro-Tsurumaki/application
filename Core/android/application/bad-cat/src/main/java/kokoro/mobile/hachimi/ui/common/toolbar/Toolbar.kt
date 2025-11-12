package kokoro.mobile.hachimi.ui.common.toolbar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kokoro.mobile.hachimi.common.ViewWithExtent
import kokoro.mobile.hachimi.common.nonScaledSp
import kokoro.mobile.hachimi.R
import kokoro.mobile.hachimi.theme.Black
import kokoro.mobile.hachimi.ui.LauncherUIManage

/**
 * Created by xianjie on 2025年1月21日10:46:50
 *
 * Description: 标题栏啊
 */

@Composable
fun Toolbar(name:String){
    val nav = LauncherUIManage.current
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)) {
        ViewWithExtent(PaddingValues(13.dp), view = {
            Icon(
                modifier = Modifier.size(22.dp),
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "返回",
            )
        }) {
            nav.navigateUp()
        }
        Text(name, modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .align(Alignment.CenterVertically)
            , fontSize = 17.nonScaledSp, color = Black, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.width(48.dp))
    }
}