package jp.ikanoshiokara.todoy.shared.data.database

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import jp.ikanoshiokara.todoy.shared.model.Task

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    @ColumnInfo(name = "is_done")
    val isDone: Boolean = false,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
)

fun TaskEntity.toModel(): Task = Task(id = id, title = title, isDone = isDone, createdAt = createdAt)

fun Task.toEntity(): TaskEntity = TaskEntity(id = id, title = title, isDone = isDone, createdAt = createdAt)
