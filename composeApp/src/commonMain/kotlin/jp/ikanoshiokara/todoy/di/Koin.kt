package jp.ikanoshiokara.todoy.di

import jp.ikanoshiokara.todoy.shared.di.sharedModule
import jp.ikanoshiokara.todoy.ui.home.HomeViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

/** UI 層 (ViewModel) の DI 定義。 */
val appModule =
    module {
        viewModelOf(::HomeViewModel)
    }

/**
 * 各プラットフォームのエントリポイントから 1 度だけ呼ぶ。
 * Android は `androidContext()` を渡すために [config] を使う。
 */
fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule, appModule)
    }
}
