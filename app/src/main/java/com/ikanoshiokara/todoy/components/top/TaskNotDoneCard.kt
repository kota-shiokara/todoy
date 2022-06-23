package com.ikanoshiokara.todoy.components.top

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ikanoshiokara.todoy.data.model.PreviewTaskProvider
import com.ikanoshiokara.todoy.data.model.Task
import com.ikanoshiokara.todoy.components.theme.TodoyTheme

@Composable
fun TaskNotDoneCard(task: Task, onClick: () -> Unit) {
    val paddingVertical = 6.dp // 縦のpadding
    val bodyHeight = 85.dp
    val bottomAccentHeight = 15.dp

    // cardの大きさ
    val cardHeight = bodyHeight +
            bottomAccentHeight +
            paddingVertical



}

@Preview(name = "Card", showBackground = true)
@Composable
fun TaskNotDoneCardPreview(
    @PreviewParameter(PreviewTaskProvider::class) task: Task
) {
    TodoyTheme {
        TaskNotDoneCard(task = task, { Log.d("a", "a")})
    }
}