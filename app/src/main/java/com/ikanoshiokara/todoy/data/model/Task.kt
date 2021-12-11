package com.ikanoshiokara.todoy.data.model


data class Task(
    val id: Int, // TaskのID, -1の時は未振り分けって判断しよう
    val title: String = "", // Taskの名前
    val description: String = "", // Taskの説明
    val isDone: Boolean = false, // タスクの終了判定
    val isRoutine: Boolean = false, // 日課判定
)
