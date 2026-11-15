package jp.ikanoshiokara.todoy

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import jp.ikanoshiokara.todoy.di.initKoin

fun main() {
    initKoin()
    application {
        Window(onCloseRequest = ::exitApplication, title = "todoy") {
            App()
        }
    }
}
