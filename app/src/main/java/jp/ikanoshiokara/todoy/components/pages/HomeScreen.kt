package jp.ikanoshiokara.todoy.components.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import jp.ikanoshiokara.todoy.components.shared.LoadingCircle
import jp.ikanoshiokara.todoy.components.shared.MainTopBar
import jp.ikanoshiokara.todoy.components.top.TaskCard
import jp.ikanoshiokara.todoy.viewmodel.HomeScreenState
import jp.ikanoshiokara.todoy.viewmodel.HomeScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    var isShowBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { MainTopBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isShowBottomSheet = true
                }
            ) {
                Icon(Icons.Filled.Add, "Add")
            }
        }
    ) { paddingValues ->
        val uiState: HomeScreenState by viewModel.state.collectAsState()

        when  {
            uiState.loading -> {
                LoadingCircle()
            }
            uiState.error != null -> {
                val errorMessage = uiState.error
                var openDialog by remember { mutableStateOf(true) }
                errorMessage?.printStackTrace()
                Log.d("MainPage", "$errorMessage")

                if (openDialog) {
                    AlertDialog(
                        onDismissRequest = { openDialog = false },
                        title = {
                            Text("Error")
                        },
                        text = {
                            Text(text = errorMessage.toString())
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    openDialog = false
                                }
                            ) {
                                Text("OK")
                            }
                        }
                    )
                }
            }
            else -> {
                if (uiState.tasks.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        items(uiState.tasks) { task ->
                            TaskCard(task = task, onClick = {
                                viewModel.updateTask(task.copy(isDone = !task.isDone))
                            })
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("タスクはありません")
                    }
                }

                if (isShowBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            isShowBottomSheet = false
                        }
                    ) {
                        AddTaskPage(
                            addTask = { task ->
                                viewModel.addTask(task)
                                isShowBottomSheet = false
                            }
                        )
                    }
                }
            }
        }
    }
}
