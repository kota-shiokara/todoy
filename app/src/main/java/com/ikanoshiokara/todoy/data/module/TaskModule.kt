package com.ikanoshiokara.todoy.data.module

import android.content.Context
import androidx.room.Room
import com.ikanoshiokara.todoy.data.model.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, TaskDatabase::class.java, "task_table").build()

    @Singleton
    @Provides
    fun provideDao(db: TaskDatabase) = db.taskDao()
}