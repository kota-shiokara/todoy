package jp.ikanoshiokara.todoy.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.ikanoshiokara.todoy.data.model.PreviewTasksProvider
import jp.ikanoshiokara.todoy.data.model.Task
import jp.ikanoshiokara.todoy.ui.theme.TodoyTheme

@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    task: Task,
    onClick: (Task) -> Unit = {}
) {
    val isDone = task.isDone

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(if (!isDone) Color.White else Color.LightGray)
            .padding(8.dp)
            .clickable { onClick(task.copy(isDone = !isDone)) }
    ) {
        val textColor = Color.Black.copy(alpha = if (!isDone) 1.0f else 0.3f)
        val textDeclaration = if (!isDone) TextDecoration.None else TextDecoration.LineThrough

        Row {
            Text(
                text = task.title,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = textDeclaration,
                color = textColor
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "#${task.id}",
                modifier = Modifier.align(Alignment.Bottom),
                fontSize = 15.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = textColor
            )
        }
        Text(
            task.description,
            fontSize = 15.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textDecoration = textDeclaration,
            color = textColor
        )
    }

    Divider()
}

@Preview(name = "card", showBackground = true)
@Composable
fun TaskCardPreview(
    @PreviewParameter(PreviewTasksProvider::class) tasks: List<Task>
) {
    TodoyTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            tasks.forEach { task ->
                TaskCard(task = task)
            }
        }
    }
}
