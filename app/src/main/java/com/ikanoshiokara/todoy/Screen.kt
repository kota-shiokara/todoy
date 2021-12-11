package com.ikanoshiokara.todoy

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.ikanoshiokara.todoy.data.model.PreviewTaskProvider
import com.ikanoshiokara.todoy.data.model.Task
import com.ikanoshiokara.todoy.ui.components.MainTopBar
import com.ikanoshiokara.todoy.ui.components.top.TopFab
import com.ikanoshiokara.todoy.ui.pages.TopPage
import com.ikanoshiokara.todoy.ui.theme.TodoyTheme

@Composable
fun Screen(modifier: Modifier, task: Task) {
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = { MainTopBar() },
            content = { TopPage(modifier = modifier, task = task) },
            floatingActionButton = { TopFab() }
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewScreen(
    @PreviewParameter(PreviewTaskProvider::class) task: Task
) {
    TodoyTheme {
        Screen(Modifier.fillMaxSize(), task)
    }
}
