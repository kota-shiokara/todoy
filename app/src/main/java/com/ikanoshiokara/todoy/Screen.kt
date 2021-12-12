package com.ikanoshiokara.todoy

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.ikanoshiokara.todoy.data.model.PreviewTasksProvider
import com.ikanoshiokara.todoy.data.model.Task
import com.ikanoshiokara.todoy.ui.components.MainTopBar
import com.ikanoshiokara.todoy.ui.components.top.TopFab
import com.ikanoshiokara.todoy.ui.pages.TopScreen
import com.ikanoshiokara.todoy.ui.theme.TodoyTheme

@Composable
fun Screen(modifier: Modifier, tasks: List<Task>) {
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = { MainTopBar() },
            content = { TopScreen(modifier = modifier, tasks = tasks) },
            floatingActionButton = { TopFab() }
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewScreen(
    @PreviewParameter(PreviewTasksProvider::class) tasks: List<Task>
) {
    TodoyTheme {
        Screen(Modifier.fillMaxSize(), tasks)
    }
}
