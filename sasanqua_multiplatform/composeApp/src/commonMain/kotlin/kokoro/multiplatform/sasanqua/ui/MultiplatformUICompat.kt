package kokoro.multiplatform.sasanqua.ui

import androidx.window.core.layout.WindowSizeClass

sealed class WindowSize(val name: String){
    data object Compact:WindowSize("Compact")
    data object Medium: WindowSize("Medium")
    data object Expanded: WindowSize("Expanded")
}

fun WindowSizeClass.widthSize():WindowSize{
    val isMedium = isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)
    val isExpanded = isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)
    return when{
        !isMedium -> WindowSize.Compact
        !isExpanded -> WindowSize.Medium
        else -> WindowSize.Expanded
    }
}