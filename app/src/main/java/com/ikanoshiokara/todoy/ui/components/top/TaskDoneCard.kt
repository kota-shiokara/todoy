package com.ikanoshiokara.todoy.ui.components.top

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import com.ikanoshiokara.todoy.ui.theme.PrimaryNotActive200
import com.ikanoshiokara.todoy.ui.theme.PrimaryNotActive500
import com.ikanoshiokara.todoy.ui.theme.PrimaryNotActive700
import com.ikanoshiokara.todoy.ui.theme.TodoyTheme


@Composable
fun TaskDoneCard(task: Task, onClick: () -> Unit) {
    val paddingVertical = 6.dp // 縦のpadding
    val bodyHeight = 85.dp
    val bottomAccentHeight = 15.dp

    val darkTheme = isSystemInDarkTheme()
    val notActivePrimary = if(darkTheme) {
        PrimaryNotActive200
    } else {
        PrimaryNotActive500
    }
    val notActivePrimaryVariantColor = PrimaryNotActive700

    // cardの大きさ
    val cardHeight = bodyHeight +
            bottomAccentHeight +
            paddingVertical

    Box(
        modifier = Modifier
            .height(cardHeight)
            .padding(horizontal = 6.dp, vertical = paddingVertical)
            .fillMaxSize()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .fillMaxSize()
        ) {
            Surface(
                color = notActivePrimaryVariantColor,
                modifier = Modifier
                    .height(bodyHeight)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    Text(task.title, fontSize = 35.sp, fontWeight = FontWeight.Bold,)
                    Text(task.description, fontSize = 15.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
            Surface(
                color = notActivePrimary,
                modifier = Modifier
                    .height(bottomAccentHeight)
                    .fillMaxSize()
            ){}
        }
    }

}

@Preview(name = "a", showBackground = true)
@Composable
fun TaskDoneCardPreview(
    @PreviewParameter(PreviewTaskProvider::class) task: Task
) {
    TodoyTheme {
        TaskDoneCard(task = task, {})
    }
}