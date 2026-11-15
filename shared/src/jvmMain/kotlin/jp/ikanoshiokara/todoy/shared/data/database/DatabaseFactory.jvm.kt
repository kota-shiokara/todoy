package jp.ikanoshiokara.todoy.shared.data.database

import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module
import java.io.File

internal actual val platformDatabaseModule =
    module {
        single<RoomDatabase.Builder<TodoyDatabase>> { createDatabaseBuilder() }
    }

private fun createDatabaseBuilder(): RoomDatabase.Builder<TodoyDatabase> {
    val dbFile = File(appDataDirectory(), TodoyDatabase.FILE_NAME)
    return Room
        .databaseBuilder<TodoyDatabase>(name = dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
}

/** OS ごとの慣習に沿ったアプリデータ保存先。 */
private fun appDataDirectory(): File {
    val os = System.getProperty("os.name").lowercase()
    val home = System.getProperty("user.home")
    val base =
        when {
            os.contains("win") -> System.getenv("APPDATA")?.let(::File) ?: File(home, "AppData/Roaming")
            os.contains("mac") -> File(home, "Library/Application Support")
            else -> System.getenv("XDG_DATA_HOME")?.let(::File) ?: File(home, ".local/share")
        }
    return File(base, "todoy").apply { mkdirs() }
}
