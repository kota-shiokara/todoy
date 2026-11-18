package jp.ikanoshiokara.todoy.shared.data.database

import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.SQLiteDriver
import org.koin.dsl.module

/**
 * Web では SQLite が Web Worker 上で動く。Worker スクリプトはアプリ (composeApp) 側の資産なので、
 * `WebWorkerSQLiteDriver` はアプリ側で生成して [SQLiteDriver] として Koin に登録してもらう。
 */
internal actual val platformDatabaseModule =
    module {
        single<RoomDatabase.Builder<TodoyDatabase>> {
            Room
                .databaseBuilder<TodoyDatabase>(name = TodoyDatabase.FILE_NAME)
                .setDriver(get<SQLiteDriver>())
        }
    }
