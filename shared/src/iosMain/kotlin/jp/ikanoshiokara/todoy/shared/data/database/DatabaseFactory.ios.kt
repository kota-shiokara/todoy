package jp.ikanoshiokara.todoy.shared.data.database

import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

internal actual val platformDatabaseModule =
    module {
        single<RoomDatabase.Builder<TodoyDatabase>> { createDatabaseBuilder() }
    }

private fun createDatabaseBuilder(): RoomDatabase.Builder<TodoyDatabase> =
    Room
        .databaseBuilder<TodoyDatabase>(name = "${documentDirectory()}/${TodoyDatabase.FILE_NAME}")
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val url =
        NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = true,
            error = null,
        )
    return requireNotNull(url?.path) { "Document directory is not available" }
}
