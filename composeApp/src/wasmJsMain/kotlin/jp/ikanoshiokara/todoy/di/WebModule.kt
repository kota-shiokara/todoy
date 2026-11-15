package jp.ikanoshiokara.todoy.di

import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.web.WebWorkerSQLiteDriver
import org.koin.dsl.module
import org.w3c.dom.Worker
import kotlin.js.ExperimentalWasmJsInterop

/** Web 固有の DI 定義。 */
val webModule =
    module {
        single<SQLiteDriver> { WebWorkerSQLiteDriver(createSqliteWorker()) }
    }

/**
 * SQLite を動かす Web Worker を生成する。
 * 実体は `src/wasmJsMain/resources/sqlite-worker.js` で、webpack が依存 (@sqlite.org/sqlite-wasm) ごとバンドルする。
 */
@OptIn(ExperimentalWasmJsInterop::class)
private fun createSqliteWorker(): Worker = js("""new Worker(new URL("./sqlite-worker.js", import.meta.url), { type: "module" })""")
