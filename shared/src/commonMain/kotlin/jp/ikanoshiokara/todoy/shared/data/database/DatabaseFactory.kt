package jp.ikanoshiokara.todoy.shared.data.database

import androidx.room3.RoomDatabase
import org.koin.core.module.Module

/**
 * プラットフォームごとの [RoomDatabase.Builder] (保存先・SQLite ドライバ・実行コンテキスト) を
 * Koin に登録するモジュール。
 */
internal expect val platformDatabaseModule: Module

internal fun RoomDatabase.Builder<TodoyDatabase>.buildTodoyDatabase(): TodoyDatabase =
    // 初期開発中はスキーマ変更のたびにマイグレーションを書かずに済むよう破壊的移行を許可する。
    // TODO: 公開前に Migration / AutoMigration を整備してこの行を外す
    fallbackToDestructiveMigration(dropAllTables = true)
        .build()
