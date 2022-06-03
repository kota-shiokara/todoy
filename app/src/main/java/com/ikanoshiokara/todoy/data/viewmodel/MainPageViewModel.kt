package com.ikanoshiokara.todoy.data.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.*
import com.ikanoshiokara.todoy.data.model.Task
import com.ikanoshiokara.todoy.data.model.TaskDao
import com.ikanoshiokara.todoy.data.model.TaskDatabase
import com.ikanoshiokara.todoy.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val taskRepository: TaskRepository
): ViewModel() {
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

    class Factory(private val repository: TaskRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MainPageViewModel(this.repository) as T
        }
    }
}

sealed class MainPageState {
    object Loading: MainPageState()
    data class Success(val content: List<Task>): MainPageState()
    data class Error(val exception: Exception): MainPageState()
    data class AddTask(val task: Task): MainPageState()
}

class PreviewTaskViewModelProvider: PreviewParameterProvider<MainPageViewModel> {
    override val values: Sequence<MainPageViewModel>
        get() {
            val context: Context? = null
            return sequenceOf(

            )
        }
}