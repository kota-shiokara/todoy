package com.ikanoshiokara.todoy.data.model

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey
    val id: Int, // TaskのID, -1の時は未振り分けって判断しよう
    val title: String = "", // Taskの名前
    val description: String = "", // Taskの説明
    var isDone: Boolean = false, // タスクの終了判定
    val isRoutine: Boolean = false, // 日課判定
)

val mockTask = Task(
    0,
    "Mock Task",
    "これはモック用のタスクです"
)

class PreviewTaskProvider: PreviewParameterProvider<Task> {
    override val values: Sequence<Task>
        get() = sequenceOf(
            Task(1, "Mock Task", "これはモック用のタスクです。"),
            Task(2, "アプリ概要の提出", "18:00 〆切")
        )
}

class PreviewTasksProvider: PreviewParameterProvider<List<Task>> {
    override val values: Sequence<List<Task>>
        get() = sequenceOf(
            listOf(
                Task(1, "Mock Task", "これはモック用のタスクです。"),
                Task(2, "アプリ概要の提出", "18:00 〆切"),
                Task(3, "Go to bed", "Oyasumi"),

            )
        )
}

