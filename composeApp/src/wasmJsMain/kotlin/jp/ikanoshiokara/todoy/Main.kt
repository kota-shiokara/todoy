package jp.ikanoshiokara.todoy

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import jp.ikanoshiokara.todoy.di.initKoin
import jp.ikanoshiokara.todoy.di.webModule

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin {
        modules(webModule)
    }
    ComposeViewport {
        App()
    }
}
