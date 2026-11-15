package jp.ikanoshiokara.todoy.shared.data.repository

import jp.ikanoshiokara.todoy.shared.data.database.TaskDao
import jp.ikanoshiokara.todoy.shared.data.database.TaskEntity
import jp.ikanoshiokara.todoy.shared.data.database.toEntity
import jp.ikanoshiokara.todoy.shared.data.database.toModel
import jp.ikanoshiokara.todoy.shared.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock

interface TaskRepository {
    fun observeTasks(): Flow<List<Task>>

    suspend fun addTask(title: String): Long

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(task: Task)
}

internal class DefaultTaskRepository(
    private val taskDao: TaskDao,
    private val clock: Clock = Clock.System,
) : TaskRepository {
    override fun observeTasks(): Flow<List<Task>> = taskDao.observeAll().map { list -> list.map { it.toModel() } }

    override suspend fun addTask(title: String): Long =
        taskDao.insert(TaskEntity(title = title, createdAt = clock.now().toEpochMilliseconds()))

    override suspend fun updateTask(task: Task) = taskDao.update(task.toEntity())

    override suspend fun deleteTask(task: Task) = taskDao.delete(task.toEntity())
}
