package kokoro.mobile.hachimi.ui.common.view

import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kokoro.mobile.hachimi.theme.Color_F3

/**
 * Created by xianjie on 2025年1月13日11:00:13
 *
 * Description:
 */
@Composable
fun ByteArrayImage(imageData: ByteArray,clickCallback:()->Unit) {
    val context = LocalContext.current
    AsyncImage(
        model = remember(imageData) {
            ImageRequest.Builder(context)
                .data(imageData)
                .crossfade(false)
                .placeholder(ColorDrawable(Color_F3.toArgb()))
                .error(ColorDrawable(Color_F3.toArgb()))
                .build()
        },

        contentDescription = null,
        modifier = Modifier.size(107.dp,43.dp).clickable {
            clickCallback.invoke()
        }
    )
}
