package jp.ikanoshiokara.todoy

import androidx.compose.ui.window.ComposeUIViewController
import jp.ikanoshiokara.todoy.di.initKoin
import platform.UIKit.UIViewController

/** Swift 側から `MainViewControllerKt.MainViewController()` として呼ぶ。 */
@Suppress("ktlint:standard:function-naming", "FunctionName")
fun MainViewController(): UIViewController = ComposeUIViewController { App() }

/** Swift 側の App 初期化時に `MainViewControllerKt.doInitKoinIos()` として 1 度だけ呼ぶ。 */
fun initKoinIos() = initKoin()
