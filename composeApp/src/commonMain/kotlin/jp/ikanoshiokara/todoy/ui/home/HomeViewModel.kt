package jp.ikanoshiokara.todoy.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.ikanoshiokara.todoy.shared.data.repository.TaskRepository
import jp.ikanoshiokara.todoy.shared.model.Task
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Success(
        val tasks: List<Task>,
    ) : HomeUiState
}

class HomeViewModel(
    private val taskRepository: TaskRepository,
) : ViewModel() {
    val uiState: StateFlow<HomeUiState> =
        taskRepository
            .observeTasks()
            .map<List<Task>, HomeUiState> { HomeUiState.Success(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HomeUiState.Loading,
            )

    fun addTask(title: String) {
        val trimmed = title.trim()
        if (trimmed.isEmpty()) return
        viewModelScope.launch { taskRepository.addTask(trimmed) }
    }

    fun toggleDone(task: Task) {
        viewModelScope.launch { taskRepository.updateTask(task.copy(isDone = !task.isDone)) }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch { taskRepository.deleteTask(task) }
    }
}
