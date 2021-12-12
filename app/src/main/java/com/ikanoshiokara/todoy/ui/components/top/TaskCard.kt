package com.ikanoshiokara.todoy.ui.components.top

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.ikanoshiokara.todoy.data.model.PreviewTaskProvider
import com.ikanoshiokara.todoy.data.model.Task
import com.ikanoshiokara.todoy.ui.theme.TodoyTheme


@Composable
fun TaskCard(task: Task, onClick: () -> Unit) {
    if (task.isDone){
        TaskDoneCard(task = task, onClick = onClick)
    } else {
        TaskNotDoneCard(task = task, onClick = onClick)
    }
}

@Preview(name = "Card", showBackground = true)
@Composable
fun TaskCardPreview(
    @PreviewParameter(PreviewTaskProvider::class) task: Task
) {
    TodoyTheme {
        TaskCard(task = task, { Log.d("a", "a")})
    }
}