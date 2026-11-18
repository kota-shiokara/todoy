package jp.ikanoshiokara.todoy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.ComposeViewport
import jp.ikanoshiokara.todoy.di.initKoin
import jp.ikanoshiokara.todoy.di.webModule
import jp.ikanoshiokara.todoy.resources.Res
import jp.ikanoshiokara.todoy.resources.noto_sans_jp_bold
import jp.ikanoshiokara.todoy.resources.noto_sans_jp_regular
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.preloadFont

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin {
        modules(webModule)
    }
    ComposeViewport {
        WithJapaneseFallbackFont {
            App()
        }
    }
}

/**
 * Compose for Web は OS のフォントを使えないため、同梱した Noto Sans JP をフォールバックフォントとして登録する。
 * 欧文は既定のフォントのまま、既定フォントにない文字 (日本語) だけ Noto Sans JP で描画される。
 * フォントの読み込みが終わるまではアプリを描画しない (□ が一瞬見えるのを防ぐ)。
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
private fun WithJapaneseFallbackFont(content: @Composable () -> Unit) {
    val regular by preloadFont(Res.font.noto_sans_jp_regular, FontWeight.Normal)
    val bold by preloadFont(Res.font.noto_sans_jp_bold, FontWeight.Bold)
    val fontFamilyResolver = LocalFontFamilyResolver.current
    var isReady by remember { mutableStateOf(false) }

    LaunchedEffect(regular, bold) {
        val loaded = listOfNotNull(regular, bold)
        if (loaded.size == 2) {
            fontFamilyResolver.preload(FontFamily(loaded))
            isReady = true
        }
    }

    if (isReady) content()
}
