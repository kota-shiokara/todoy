package jp.ikanoshiokara.todoy.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ikanoshiokara.todoy.data.model.Task
import jp.ikanoshiokara.todoy.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    val state = MutableStateFlow(HomeScreenState())

    init {
        getTasks()
    }

    fun getTasks() {
        viewModelScope.launch {
            state.update { it.copy(loading = true) }
            try {
                val tasks = taskRepository.getTaskAll()
                state.update {
                    it.copy(loading = false, tasks = tasks)
                }
            } catch (e: Exception) {
                state.update {
                    it.copy(loading = false, error = e)
                }
            }
        }
    }

    fun addTask(item: Task) {
        viewModelScope.launch {
            state.update { it.copy(loading = true) }
            try {
                taskRepository.insertTask(item)
            } catch (e: Exception) {
                state.update {
                    it.copy(loading = false, error = e)
                }
            }
        }.invokeOnCompletion {
            state.update { it.copy(loading = false) }
            getTasks()
        }
    }

    fun updateTask(item: Task) {
        viewModelScope.launch {
            state.update { it.copy(loading = true) }
            try {
                taskRepository.updateTask(item)
            } catch (e: Exception) {
                state.update {
                    it.copy(loading = false, error = e)
                }
            }
        }.invokeOnCompletion {
            state.update { it.copy(loading = false) }
            getTasks()
        }
    }

    fun deleteTask(id: Int) {
        viewModelScope.launch {
            state.update { it.copy(loading = true) }
            taskRepository.deleteTask(id)
        }.invokeOnCompletion {
            state.update { it.copy(loading = false) }
            getTasks()
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            state.update { it.copy(loading = true) }
            taskRepository.deleteTask(task.id)
        }.invokeOnCompletion {
            state.update { it.copy(loading = false) }
            getTasks()
        }
    }
}

data class HomeScreenState(
    val loading: Boolean = false,
    val tasks: List<Task> = listOf(),
    val error: Throwable? = null
)
