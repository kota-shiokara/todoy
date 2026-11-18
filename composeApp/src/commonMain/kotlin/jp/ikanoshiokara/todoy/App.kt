package jp.ikanoshiokara.todoy

import androidx.compose.runtime.Composable
import jp.ikanoshiokara.todoy.navigation.TodoyNavHost
import jp.ikanoshiokara.todoy.ui.theme.TodoyTheme

/** 全プラットフォーム共通のルート Composable。Koin は呼び出し前に [initKoin] で起動しておくこと。 */
@Composable
fun App() {
    TodoyTheme {
        TodoyNavHost()
    }
}
