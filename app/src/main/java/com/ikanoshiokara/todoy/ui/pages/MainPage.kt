package com.ikanoshiokara.todoy.ui.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ikanoshiokara.todoy.data.model.Task
import com.ikanoshiokara.todoy.ui.components.top.TaskCard
import com.ikanoshiokara.todoy.ui.components.top.TopFab

@Composable
fun MainPage(navController: NavController, tasks: List<Task>) {

    Scaffold(
        floatingActionButton = { TopFab(navController) }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(tasks) { task ->
                TaskCard(task = task, onClick = {
                    task.isDone = !task.isDone
                })
            }
//            items(tasksList) { item ->
//                TaskCard(task = item)
//            }
        }
    }
}