package jp.ikanoshiokara.todoy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ikanoshiokara.todoy.data.model.Task
import jp.ikanoshiokara.todoy.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    private val _state = MutableStateFlow<MainPageState>(MainPageState.Loading)
    val state = _state.asStateFlow()

    init {
        getTasks()
    }

    fun getTasks() {
        viewModelScope.launch {
            try {
                val tasks = taskRepository.getTaskAll()
                _state.value = MainPageState.Success(tasks)
            } catch (e: Exception) {
                _state.value = MainPageState.Error(e)
            }
//            _items.postValue(taskRepository.getTaskAll())
        }
    }

    fun addTask(item: Task) {
        viewModelScope.launch {
            try {
                taskRepository.insertTask(item)
            } catch (e: Exception) {
                _state.value = MainPageState.Error(e)
            }
            getTasks()
        }
    }

    fun updateTask(item: Task) {
        viewModelScope.launch {
            try {
                taskRepository.updateTask(item)
            } catch (e: Exception) {
                _state.value = MainPageState.Error(e)
            }
            getTasks()
        }
    }

    fun deleteTask(id: Int) {
        viewModelScope.launch {
            taskRepository.deleteTask(id)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task.id)
        }
    }
}

sealed class MainPageState {
    object Loading : MainPageState()
    data class Success(val content: List<Task>) : MainPageState()
    data class Error(val exception: Exception) : MainPageState()
}
