package com.ikanoshiokara.todoy.components.pages

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikanoshiokara.todoy.LocalBottomSheetItem
import com.ikanoshiokara.todoy.LocalNavController
import com.ikanoshiokara.todoy.data.viewmodel.MainPageState
import com.ikanoshiokara.todoy.data.viewmodel.MainPageViewModel
import com.ikanoshiokara.todoy.components.shared.MainTopBar
import com.ikanoshiokara.todoy.components.shared.LoadingCircle
import com.ikanoshiokara.todoy.components.top.TaskCard

@Composable
fun MainPage() {
    val navController = LocalNavController.current
    val viewModel: MainPageViewModel = hiltViewModel()
    val bottomSheet = LocalBottomSheetItem.current

    Scaffold(
        topBar = { MainTopBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                bottomSheet(BottomSheetItem.State.AddTask(addTask = {
                    viewModel.addTask(it)
                }))
            }) {
                Icon(Icons.Filled.Add, "Add")
            }
        }
    ) {
        val state: MainPageState by viewModel.state.collectAsState(initial = MainPageState.Loading)

        when(state) {
            is MainPageState.Loading -> {
                LoadingCircle()
            }
            is MainPageState.Success -> {
                val tasks = (state as MainPageState.Success).content
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    if(tasks.isNotEmpty()) {
                        items(tasks) { task ->
                            TaskCard(task = task, onClick = {
                                viewModel.updateTask(task.copy(isDone = !task.isDone))
                            })
                        }
                    } else {
                        item {
                            Text("タスクはありません")
                        }
                    }
                }
            }
            is MainPageState.Error -> {
                val errorMessage = (state as MainPageState.Error).exception
                val openDialog = remember { mutableStateOf(true) }
                errorMessage.printStackTrace()
                Log.d("MainPage", "$errorMessage")

                if(openDialog.value) {
                    AlertDialog(
                        onDismissRequest = { },
                        title = {
                            Text("Error")
                        },
                        text = {
                            Text(text = errorMessage.toString())
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    openDialog.value = false
                                }
                            ) {
                                Text("OK")
                            }
                        }
                    )
                }
            }
        }
    }
}