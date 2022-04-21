package com.ikanoshiokara.todoy.ui.pages

import android.widget.Spinner
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ikanoshiokara.todoy.LocalNavController
import com.ikanoshiokara.todoy.data.model.Task
import com.ikanoshiokara.todoy.data.viewmodel.MainPageState
import com.ikanoshiokara.todoy.data.viewmodel.MainPageViewModel
import com.ikanoshiokara.todoy.ui.components.MainTopBar
import com.ikanoshiokara.todoy.ui.components.shared.LoadingCircle
import com.ikanoshiokara.todoy.ui.components.top.TaskCard
import com.ikanoshiokara.todoy.ui.components.top.TopFab

@Composable
fun MainPage(tasks: List<Task>) {
    val navController = LocalNavController.current
    val viewModel: MainPageViewModel = hiltViewModel()

    Scaffold(
        topBar = { MainTopBar() },
        floatingActionButton = { TopFab() }
    ) {
        when(viewModel.state.value) {
            is MainPageState.Loading -> {
                LoadingCircle()
            }
            is MainPageState.Success -> {
                val tasks = (viewModel.state.value as MainPageState.Success).content
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(tasks) { task ->
                        TaskCard(task = task, onClick = {
                            task.isDone = !task.isDone
                        })
                    }
                }
            }
            is MainPageState.Error -> {

            }
        }
    }
}