package com.ikanoshiokara.todoy.ui.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.ikanoshiokara.todoy.data.model.PreviewTaskProvider
import com.ikanoshiokara.todoy.data.model.Task
import com.ikanoshiokara.todoy.ui.components.TaskCard
import com.ikanoshiokara.todoy.ui.theme.TodoyTheme

@Composable
fun TopPage(modifier: Modifier, task: Task) {
    LazyColumn {
        items(2) {
            TaskCard(task = task)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopPage(
    @PreviewParameter(PreviewTaskProvider::class) task: Task
) {
    TodoyTheme {
        TopPage(modifier = Modifier.fillMaxSize(), task = task)
    }
}
