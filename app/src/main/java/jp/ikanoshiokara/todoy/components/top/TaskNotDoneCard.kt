package jp.ikanoshiokara.todoy.components.top

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import jp.ikanoshiokara.todoy.components.theme.TodoyTheme
import jp.ikanoshiokara.todoy.data.model.PreviewTaskProvider
import jp.ikanoshiokara.todoy.data.model.Task

@Composable
fun TaskNotDoneCard(task: Task, onClick: () -> Unit) {
    val paddingVertical = 6.dp // 縦のpadding
    val bodyHeight = 85.dp
    val bottomAccentHeight = 15.dp

    // cardの大きさ
    val cardHeight = bodyHeight + bottomAccentHeight + paddingVertical
}

@Preview(name = "Card", showBackground = true)
@Composable
fun TaskNotDoneCardPreview(
    @PreviewParameter(PreviewTaskProvider::class) task: Task
) {
    TodoyTheme {
        TaskNotDoneCard(task = task) { Log.d("a", "a") }
    }
}
