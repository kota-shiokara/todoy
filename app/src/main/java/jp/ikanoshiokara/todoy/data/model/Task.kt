package jp.ikanoshiokara.todoy.data.model

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val isDone: Boolean = false,
    val isRoutine: Boolean = false
)

class PreviewTasksProvider : PreviewParameterProvider<List<Task>> {
    private val isDoneTarget = listOf(2)
    private val isRoutineTarget = listOf(3)

    override val values: Sequence<List<Task>>
        get() = sequenceOf(
            (1..8).map {
                Task(
                    id = it,
                    title = "mock",
                    description = "Description".repeat(it),
                    isDone = isDoneTarget.contains(it),
                    isRoutine = isRoutineTarget.contains(it)
                )
            }
        )
}
