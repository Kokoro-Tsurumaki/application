package kokoro.mobile.template.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

//val Purple80 = Color(0xFFD0BCFF)
//val PurpleGrey80 = Color(0xFFCCC2DC)
//val Pink80 = Color(0xFFEFB8C8)
//
//val Purple40 = Color(0xFF6650a4)
//val PurpleGrey40 = Color(0xFF625b71)
//val Pink40 = Color(0xFF7D5260)

val White                     =                     Color(0xFFFFFFFF)
val White_Background          =                     Color(0xFFFAFAFA)
val White_33                  =                     Color(0x33ffffff)
val Black                     =                     Color(0xFF000000)
val TransparentBlack          =                     Color(0x80000000)
val TransparentWhite          =                     Color(0x80FFFFFF)

val MainThemeColor            =                      Color(0xFF08C4CA)
val Yellow                    =                      Color(0xFFf6c240)
val Color333                  =                      Color(0xFF333333)
val Color_F3                  =                      Color(0xFFf3f3f3)
val Color_999                 =                      Color(0xFF999999)
val WHITE_100                 =                      Color(0xFFF5F5F5)


val RED_100                   =                      Color(0xFFFFCDD2)
val PINK_100                  =                      Color(0xFFF8BBD0)
val PINK_111                  =                      Color(0xFFE3CAD0)
val PURPLE_100                =                      Color(0xFFE1BEE7)
val DEEP_PURPLE_100           =                      Color(0xFFD1C4E9)
val INDIGO_100                =                      Color(0xFFC5CAE9)
val BLUE_100                  =                      Color(0xFFBBDEFB)
val LIGHT_BLUE_100            =                      Color(0xFFB3E5FC)
val CYAN_100                  =                      Color(0xFFB2EBF2)
val TEAL_100                  =                      Color(0xFFB2DFDB)
val GREEN_100                 =                      Color(0xFFC8E6C9)
val LIGHT_GREEN_100           =                      Color(0xFFDCEDC8)
val LIME_100                  =                      Color(0xFFF0F4C3)
val YELLOW_100                =                      Color(0xFFFFF9C4)
val AMBER_100                 =                      Color(0xFFFFECB3)
val ORANGE_100                =                      Color(0xFFFFE0B2)
val DEEP_ORANGE_100           =                      Color(0xFFFFCCBC)
val BROWN_100                 =                      Color(0xFFD7CCC8)
val GREY_100                  =                      Color(0xFFF5F5F5)
val BLUE_GREY_100             =                      Color(0xFFCFD8DC)

val material100List = arrayListOf(RED_100, PINK_100,PURPLE_100,DEEP_PURPLE_100,INDIGO_100,BLUE_100,
    LIGHT_BLUE_100, CYAN_100, TEAL_100, GREEN_100, LIGHT_GREEN_100, LIME_100, YELLOW_100, AMBER_100,
    ORANGE_100, DEEP_ORANGE_100, BROWN_100, GREY_100, BLUE_GREY_100)

val RED_500                   =                      Color(0xFFF44336)
val PINK_500                  =                      Color(0xFFE91E63)
val PURPLE_500                =                      Color(0xFF9C27B0)
val DEEP_PURPLE_500           =                      Color(0xFF673AB7)
val INDIGO_500                =                      Color(0xFF3F51B5)
val BLUE_500                  =                      Color(0xFF2196F3)
val LIGHT_BLUE_500            =                      Color(0xFF03A9F4)
val CYAN_500                  =                      Color(0xFF00BCD4)
val TEAL_500                  =                      Color(0xFF009688)
val GREEN_500                 =                      Color(0xFF4CAF50)
val LIGHT_GREEN_500           =                      Color(0xFF8BC34A)
val LIME_500                  =                      Color(0xFFCDDC39)
val YELLOW_500                =                      Color(0xFFFFEB3B)
val AMBER_500                 =                      Color(0xFFFFC107)
val ORANGE_500                =                      Color(0xFFFF9800)
val DEEP_ORANGE_500           =                      Color(0xFFFF5722)
val BROWN_500                 =                      Color(0xFF795548)
val GREY_500                  =                      Color(0xFF9E9E9E)
val BLUE_GREY_500             =                      Color(0xFF607D8B)

val material500List = arrayListOf(
    RED_500, PINK_500, PURPLE_500, DEEP_PURPLE_500, INDIGO_500, BLUE_500,
    LIGHT_BLUE_500, CYAN_500, TEAL_500, GREEN_500, LIGHT_GREEN_500, LIME_500, YELLOW_500, AMBER_500,
    ORANGE_500, DEEP_ORANGE_500, BROWN_500, GREY_500, BLUE_GREY_500
)

var lastColor = 0
fun random500Color(): Color {
    var numberInRange = Random.nextInt(0, material500List.size-1)
    while (numberInRange == lastColor){
        numberInRange = Random.nextInt(0, material500List.size-1)
    }
    lastColor = numberInRange
    return material500List[numberInRange]
}


@Stable
val Color_77838f = Color(0xFF77838F)
@Stable
val color_d7dee0 = Color(0xFFd7dee0)

// 定义颜色集合
object AppColors {
    // 使用 CompositionLocal 管理颜色
    val localColors= staticCompositionLocalOf {
        AppColorsScheme(Black)
    }
}

// 定义颜色方案数据类
data class AppColorsScheme(
    val mainColor: Color,
    // ... 其他颜色
)


