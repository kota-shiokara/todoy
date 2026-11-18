package jp.ikanoshiokara.todoy.shared.data

import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import jp.ikanoshiokara.todoy.shared.data.database.TodoyDatabase
import jp.ikanoshiokara.todoy.shared.data.repository.DefaultTaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.Instant

class TaskRepositoryTest {
    private lateinit var database: TodoyDatabase
    private lateinit var repository: DefaultTaskRepository

    private val fixedClock =
        object : Clock {
            var now = Instant.fromEpochMilliseconds(1_000)

            override fun now(): Instant = now
        }

    @BeforeTest
    fun setUp() {
        database =
            Room
                .inMemoryDatabaseBuilder<TodoyDatabase>()
                .setDriver(BundledSQLiteDriver())
                .setQueryCoroutineContext(Dispatchers.IO)
                .build()
        repository = DefaultTaskRepository(database.taskDao(), fixedClock)
    }

    @AfterTest
    fun tearDown() {
        database.close()
    }

    @Test
    fun addedTasksAreObservedNewestFirst() =
        runTest {
            repository.addTask("first")
            fixedClock.now = Instant.fromEpochMilliseconds(2_000)
            repository.addTask("second")

            val tasks = repository.observeTasks().first()

            assertEquals(listOf("second", "first"), tasks.map { it.title })
            assertEquals(listOf(2_000L, 1_000L), tasks.map { it.createdAt })
        }

    @Test
    fun updateAndDeleteAreReflected() =
        runTest {
            repository.addTask("task")
            val task = repository.observeTasks().first().single()

            repository.updateTask(task.copy(isDone = true))
            assertTrue(
                repository
                    .observeTasks()
                    .first()
                    .single()
                    .isDone,
            )

            repository.deleteTask(task)
            assertTrue(repository.observeTasks().first().isEmpty())
        }
}
