package jp.ikanoshiokara.todoy.shared.model

/**
 * アプリ全体で扱うタスクのドメインモデル。
 * DB のエンティティ ([jp.ikanoshiokara.todoy.shared.data.database.TaskEntity]) とは分離しておく。
 */
data class Task(
    val id: Long,
    val title: String,
    val isDone: Boolean,
    /** 作成日時 (エポックミリ秒) */
    val createdAt: Long,
)
