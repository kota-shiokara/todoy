package jp.ikanoshiokara.todoy.data.repository

import jp.ikanoshiokara.todoy.data.model.Task
import jp.ikanoshiokara.todoy.data.model.TaskDao
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) : ITaskRepository {
    override suspend fun insertTask(item: Task) = withContext(IO) { taskDao.insertTask(item) }
    override suspend fun getTaskAll() = withContext(IO) { taskDao.getTaskAll() }
    override suspend fun getTask(id: Int) = withContext(IO) { taskDao.getTask(id) }
    override suspend fun deleteTaskAll() = withContext(IO) { taskDao.deleteTaskAll() }
    override suspend fun updateTask(item: Task) = withContext(IO) { taskDao.updateTask(item) }
    override suspend fun deleteTask(id: Int) = withContext(IO) { taskDao.deleteTask(id) }
}

interface ITaskRepository {
    suspend fun insertTask(item: Task)
    suspend fun getTaskAll(): List<Task>
    suspend fun getTask(id: Int): Task
    suspend fun deleteTaskAll()
    suspend fun updateTask(item: Task)
    suspend fun deleteTask(id: Int)
}
