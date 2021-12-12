package com.ikanoshiokara.todoy.data.repository

import com.ikanoshiokara.todoy.data.model.Task
import com.ikanoshiokara.todoy.data.model.TaskDao
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class TaskRepository(private val taskDao: TaskDao) {
    suspend fun insertTask(item: Task) = withContext(IO) { taskDao.insertTask(item) }
    suspend fun getTaskAll() = withContext(IO){ taskDao.getTaskAll() }
    suspend fun getTask(id: Int) = withContext(IO){ taskDao.getTask(id) }
    suspend fun deleteTaskAll() = withContext(IO){ taskDao.deleteTaskAll() }
    suspend fun updateTask(item: Task) = withContext(IO){ taskDao.updateTask(item) }
    suspend fun deleteTask(id: Int) = withContext(IO){ taskDao.deleteTask(id) }
}

