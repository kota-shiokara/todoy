package com.ikanoshiokara.todoy.data.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikanoshiokara.todoy.data.model.Task
import com.ikanoshiokara.todoy.data.repository.TaskRepository
import kotlinx.coroutines.launch

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
}