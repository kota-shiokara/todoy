package com.ikanoshiokara.todoy.data.viewmodel

import android.content.Context
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.*
import com.ikanoshiokara.todoy.data.model.Task
import com.ikanoshiokara.todoy.data.model.TaskDao
import com.ikanoshiokara.todoy.data.model.TaskDatabase
import com.ikanoshiokara.todoy.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class TaskViewModel(
    private val taskRepository: TaskRepository
): ViewModel() {
    private val _items: MutableLiveData<List<Task>> = MutableLiveData()
    val items: LiveData<List<Task>> get() = _items

    fun getTasks() {
        viewModelScope.launch {
            _items.postValue(taskRepository.getTaskAll())
        }
    }

    fun addTask(item: Task) {
        viewModelScope.launch {
            taskRepository.insertTask(item)
            getTasks()
        }
    }

    fun updataTask(item: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(item)
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
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(this.repository) as T
        }
    }
}

class PreviewTaskViewModelProvider: PreviewParameterProvider<TaskViewModel> {
    override val values: Sequence<TaskViewModel>
        get() {
            val context: Context? = null
            return sequenceOf(

            )
        }
}