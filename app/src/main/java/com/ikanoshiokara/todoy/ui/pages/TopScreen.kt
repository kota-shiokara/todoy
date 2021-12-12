package com.ikanoshiokara.todoy.ui.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.ikanoshiokara.todoy.data.model.PreviewTasksProvider
import com.ikanoshiokara.todoy.data.model.Task
import com.ikanoshiokara.todoy.ui.components.TaskCard
import com.ikanoshiokara.todoy.ui.theme.TodoyTheme

@Composable
fun TopScreen(modifier: Modifier, tasks: List<Task>) {
    LazyColumn {
        items(tasks) { task ->
            TaskCard(task = task)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopPage(
    @PreviewParameter(PreviewTasksProvider::class) tasks: List<Task>
) {
    TodoyTheme {
        TopScreen(modifier = Modifier.fillMaxSize(), tasks = tasks)
    }
}
