package kokoro.mobile.template.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.Modifier

/**
 * Created by xianjie on 2025年1月8日10:05:30
 *
 * Description:
 */
object Modifier {
    //最大且处理状态栏和导航栏
    val scaffoldModifier = Modifier
        .navigationBarsPadding()
        .fillMaxSize()



    fun scaffoldFullModifier(innerPadding:PaddingValues) = Modifier
        .navigationBarsPadding()
        .fillMaxSize()
}