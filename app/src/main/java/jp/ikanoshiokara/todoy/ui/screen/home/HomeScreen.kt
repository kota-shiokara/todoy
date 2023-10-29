package jp.ikanoshiokara.todoy.ui.screen.home

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import jp.ikanoshiokara.todoy.data.model.Task
import jp.ikanoshiokara.todoy.ui.screen.AddTaskPage
import jp.ikanoshiokara.todoy.ui.theme.TodoyTheme
import jp.ikanoshiokara.todoy.ui.util.LoadingCircle
import jp.ikanoshiokara.todoy.ui.util.TodoyAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val uiState: HomeScreenState by viewModel.state.collectAsState()

    HomeContent(
        uiState = uiState,
        updateTask = { task ->
            viewModel.updateTask(task)
        },
        addTask = { task ->
            viewModel.addTask(task)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    uiState: HomeScreenState,
    updateTask: (Task) -> Unit = {},
    addTask: (Task) -> Unit = {}
) {
    var isShowBottomSheet by remember { mutableStateOf(false) }
    Scaffold(
        topBar = { TodoyAppBar() },
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
        when {
            uiState.loading -> {
                LoadingCircle()
            }
            uiState.error != null -> {
                val errorMessage = uiState.error
                var openDialog by remember { mutableStateOf(true) }
                errorMessage.printStackTrace()

                if (openDialog) {
                    AlertDialog(
                        onDismissRequest = { openDialog = false },
                        title = {
                            Text("Error")
                        },
                        text = {
                            errorMessage.message?.let { Text(text = it) }
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
                            TaskCard(
                                task = task,
                                onClick = { task ->
                                    updateTask(task)
                                }
                            )
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
                                addTask(task)
                                isShowBottomSheet = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    TodoyTheme {
        HomeContent(
            uiState = HomeScreenState(
                tasks = (1..10).map {
                    Task(
                        id = it,
                        title = "Mock",
                        description = "description".repeat(it),
                        isDone = it == 2
                    )
                }
            )
        )
    }
}

@Preview
@Composable
fun HomeScreen_NotTaskPreview() {
    TodoyTheme {
        HomeContent(
            uiState = HomeScreenState(
                tasks = listOf()
            )
        )
    }
}

@Preview
@Composable
fun HomeScreen_ErrorPreview() {
    TodoyTheme {
        HomeContent(
            uiState = HomeScreenState(
                error = Throwable("Mock Error")
            )
        )
    }
}
