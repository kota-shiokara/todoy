package jp.ikanoshiokara.todoy.shared.data.database

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor

@Database(
    entities = [TaskEntity::class],
    version = 1,
    exportSchema = true,
)
@ConstructedBy(TodoyDatabaseConstructor::class)
abstract class TodoyDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        const val FILE_NAME = "todoy.db"
    }
}

// Room のコンパイラが各プラットフォーム向けに actual 実装を生成する
@Suppress("KotlinNoActualForExpect")
expect object TodoyDatabaseConstructor : RoomDatabaseConstructor<TodoyDatabase> {
    override fun initialize(): TodoyDatabase
}
