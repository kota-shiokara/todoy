package jp.ikanoshiokara.todoy.ui.screen.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.ikanoshiokara.todoy.data.model.PreviewTaskProvider
import jp.ikanoshiokara.todoy.data.model.Task
import jp.ikanoshiokara.todoy.ui.theme.TodoyTheme

@Composable
fun TaskCard(task: Task, onClick: () -> Unit) {
    val paddingVertical = 6.dp // 縦のpadding
    val bodyHeight = 85.dp
    val bottomAccentHeight = 15.dp

    val darkTheme = isSystemInDarkTheme()

    val (contentAlpha, textDecoration, backgroundColor, textColor) = if (task.isDone) {
        if (darkTheme) {
            TaskCardColor(
                contentAlpha = ContentAlpha.disabled,
                textDecoration = TextDecoration.LineThrough,
                backgroundColor = Color.DarkGray,
                textColor = Color.LightGray
            )
        } else {
            TaskCardColor(
                contentAlpha = ContentAlpha.disabled,
                textDecoration = TextDecoration.LineThrough,
                backgroundColor = Color.LightGray,
                textColor = Color.DarkGray
            )
        }
    } else {
        if (darkTheme) {
            TaskCardColor(
                contentAlpha = ContentAlpha.high,
                textDecoration = TextDecoration.None,
                backgroundColor = Color.Black,
                textColor = Color.White
            )
        } else {
            TaskCardColor(
                contentAlpha = ContentAlpha.high,
                textDecoration = TextDecoration.None,
                backgroundColor = Color.White,
                textColor = Color.Black
            )
        }
    }

    // cardの大きさ
    val cardHeight = bodyHeight + bottomAccentHeight + paddingVertical

    Box(
        modifier = Modifier
            .height(cardHeight)
            .background(backgroundColor)
            .padding(horizontal = 6.dp, vertical = paddingVertical)
            .fillMaxSize()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .alpha(contentAlpha)
        ) {
            Text(
                task.title,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = textDecoration,
                color = textColor
            )
            Text(
                task.description,
                fontSize = 15.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textDecoration = textDecoration,
                color = textColor
            )
        }
    }

    Divider()
}

data class TaskCardColor(
    val contentAlpha: Float,
    val textDecoration: TextDecoration,
    val backgroundColor: Color,
    val textColor: Color
)

@Preview(name = "card", showBackground = true)
@Composable
fun TaskCardPreview(
    @PreviewParameter(PreviewTaskProvider::class) task: Task
) {
    TodoyTheme {
        Column {
            TaskCard(task = task) { Log.d("a", "a") }
            TaskCard(task = task.copy(isDone = true)) {
                Log.d("a", "a")
            }
        }
    }
}

@Preview(name = "dark card", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TaskCardDarkPreview(
    @PreviewParameter(PreviewTaskProvider::class) task: Task
) {
    TodoyTheme {
        Column {
            TaskCard(task = task) { Log.d("a", "a") }
            TaskCard(task = task.copy(isDone = true)) {
                Log.d("a", "a")
            }
        }
    }
}
