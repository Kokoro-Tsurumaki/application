package kokoro.multiplatform.sasanqua.theme.default

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import sasanqua_multiplatform.composeapp.generated.resources.Res
import sasanqua_multiplatform.composeapp.generated.resources.noto_sans_sc_bold
import sasanqua_multiplatform.composeapp.generated.resources.noto_sans_sc_light
import sasanqua_multiplatform.composeapp.generated.resources.noto_sans_sc_medium
import sasanqua_multiplatform.composeapp.generated.resources.noto_sans_sc_regular




@Composable
fun webTypography(): Typography {
    val notoSansFamily = FontFamily(
        Font(Res.font.noto_sans_sc_light, FontWeight.Light),
        Font(Res.font.noto_sans_sc_regular, FontWeight.Normal),
        Font(Res.font.noto_sans_sc_light, FontWeight.Normal, FontStyle.Italic),
        Font(Res.font.noto_sans_sc_medium, FontWeight.Medium),
        Font(Res.font.noto_sans_sc_bold, FontWeight.Bold)
    )


    return Typography(
        bodySmall = MaterialTheme.typography.bodySmall.copy(
            fontFamily = notoSansFamily,
        ),
        bodyMedium = MaterialTheme.typography.bodyMedium.copy(
            fontFamily = notoSansFamily,
        ),
        bodyLarge = MaterialTheme.typography.bodyLarge.copy(
            fontFamily = notoSansFamily,
        ),
        titleSmall = MaterialTheme.typography.titleSmall.copy(
            fontFamily = notoSansFamily,
        ),
        titleMedium = MaterialTheme.typography.titleMedium.copy(
            fontFamily = notoSansFamily,
        ),
        titleLarge = MaterialTheme.typography.titleLarge.copy(
            fontFamily = notoSansFamily,
        ),
        headlineSmall = MaterialTheme.typography.headlineSmall.copy(
            fontFamily = notoSansFamily,
        ),
        headlineMedium = MaterialTheme.typography.headlineMedium.copy(
            fontFamily = notoSansFamily,
        ),
        headlineLarge = MaterialTheme.typography.headlineLarge.copy(
            fontFamily = notoSansFamily,
        ),
        displaySmall = MaterialTheme.typography.displaySmall.copy(
            fontFamily = notoSansFamily,
        ),
        displayMedium = MaterialTheme.typography.displayMedium.copy(
            fontFamily = notoSansFamily,
        ),
        displayLarge = MaterialTheme.typography.displayLarge.copy(
            fontFamily = notoSansFamily,
        ),
        labelSmall = MaterialTheme.typography.labelSmall.copy(
            fontFamily = notoSansFamily,
        ),
        labelMedium = MaterialTheme.typography.labelMedium.copy(
            fontFamily = notoSansFamily,
        ),
        labelLarge = MaterialTheme.typography.labelLarge.copy(
            fontFamily = notoSansFamily,
        ),
    )
}