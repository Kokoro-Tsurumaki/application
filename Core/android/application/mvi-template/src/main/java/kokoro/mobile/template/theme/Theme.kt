package kokoro.mobile.template.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = MainThemeColor,
    secondary = MainThemeColor,
    tertiary = MainThemeColor,
    //背景颜色
    background = White_Background,
    //dialog等背景颜色
    surface = White,
)

private val LightColorScheme = lightColorScheme(
    primary = MainThemeColor,
    secondary = MainThemeColor,
    tertiary = MainThemeColor,
    //背景颜色
    background = White_Background,
    //dialog等背景颜色
    surface = White
)

@Composable
fun KokoroTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // 不要用系统的动态系统色 /Android12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
//        禁用深色
//        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val colors = if (darkTheme) {
        AppColorsScheme(
//            mainColor =  Black
            mainColor = White
        )
    } else {
        AppColorsScheme(
            mainColor = White
        )
    }

    CompositionLocalProvider(AppColors.localColors provides colors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }


}