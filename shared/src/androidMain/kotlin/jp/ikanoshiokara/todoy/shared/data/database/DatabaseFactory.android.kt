package jp.ikanoshiokara.todoy.shared.data.database

import android.content.Context
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal actual val platformDatabaseModule =
    module {
        single<RoomDatabase.Builder<TodoyDatabase>> { createDatabaseBuilder(androidContext()) }
    }

private fun createDatabaseBuilder(context: Context): RoomDatabase.Builder<TodoyDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(TodoyDatabase.FILE_NAME)
    return Room
        .databaseBuilder<TodoyDatabase>(context = appContext, name = dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
}
