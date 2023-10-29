package jp.ikanoshiokara.todoy.ui.util

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import jp.ikanoshiokara.todoy.R
import jp.ikanoshiokara.todoy.ui.theme.TodoyTheme

@Composable
fun TodoyAppBar(
    title: String = stringResource(id = R.string.app_name)
) {
    TopAppBar(
        title = {
            Text(title)
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMainTopBar() {
    TodoyTheme {
        TodoyAppBar()
    }
}
