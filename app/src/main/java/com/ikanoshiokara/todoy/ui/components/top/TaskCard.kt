package com.ikanoshiokara.todoy.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ikanoshiokara.todoy.data.model.PreviewTaskProvider
import com.ikanoshiokara.todoy.data.model.Task
import com.ikanoshiokara.todoy.ui.theme.TodoyTheme


@Composable
fun TaskCard(task: Task) {
    val paddingVertical = 6.dp // 縦のpadding
    val bodyHeight = 70.dp
    val bottomAccentHeight = 30.dp

    // cardの大きさ
    val cardHeight = bodyHeight +
            bottomAccentHeight +
            paddingVertical

    Box(
        modifier = Modifier
            .height(cardHeight)
            .padding(horizontal = 6.dp, vertical = paddingVertical)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .fillMaxSize()
        ) {
            Surface(
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .height(bodyHeight)
                    .fillMaxSize()
            ) {
                Text(task.title, fontSize = 35.sp)
            }
            Surface(
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .height(bottomAccentHeight)
                    .fillMaxSize()
            ){}
        }
    }

}

@Preview(name = "Card", showBackground = true)
@Composable
fun TaskCardPreview(
    @PreviewParameter(PreviewTaskProvider::class) task: Task
) {
    TodoyTheme {
        TaskCard(task = task)
    }
}