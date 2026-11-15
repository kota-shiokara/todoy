package jp.ikanoshiokara.todoy.shared.di

import androidx.room3.RoomDatabase
import jp.ikanoshiokara.todoy.shared.data.database.TodoyDatabase
import jp.ikanoshiokara.todoy.shared.data.database.buildTodoyDatabase
import jp.ikanoshiokara.todoy.shared.data.database.platformDatabaseModule
import jp.ikanoshiokara.todoy.shared.data.repository.DefaultTaskRepository
import jp.ikanoshiokara.todoy.shared.data.repository.TaskRepository
import org.koin.dsl.module

/** データ層 (DB・Repository) の DI 定義。 */
val sharedModule =
    module {
        includes(platformDatabaseModule)
        single<TodoyDatabase> { get<RoomDatabase.Builder<TodoyDatabase>>().buildTodoyDatabase() }
        single { get<TodoyDatabase>().taskDao() }
        single<TaskRepository> { DefaultTaskRepository(get()) }
    }
