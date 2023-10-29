package jp.ikanoshiokara.todoy.ui.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import jp.ikanoshiokara.todoy.ui.theme.TodoyTheme

@Composable
fun LoadingCircle(
    modifier: Modifier = Modifier,
    color: Color = Color.Gray
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = color)
    }
}

@Preview
@Composable
fun LoadingCirclePreview() {
    TodoyTheme {
        LoadingCircle()
    }
}
