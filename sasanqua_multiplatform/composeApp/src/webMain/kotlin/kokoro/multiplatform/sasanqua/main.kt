package kokoro.multiplatform.sasanqua

import androidx.compose.ui.*
import androidx.compose.ui.window.*

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        println("WebRoot")
        App()
    }
}