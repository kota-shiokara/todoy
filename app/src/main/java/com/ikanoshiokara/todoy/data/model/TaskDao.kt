package com.ikanoshiokara.todoy.data.model

import androidx.room.*


@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTask(item: Task)

    @Query("select * from task_table")
    fun getTaskAll(): List<Task>

    @Query("select * from task_table where id=:id")
    fun getTask(id: Int): Task

    @Query("delete from task_table")
    fun deleteTaskAll()

    @Update
    fun updateTask(item: Task)

    @Query("delete from task_table where id=:id")
    fun deleteTask(id: Int)
}