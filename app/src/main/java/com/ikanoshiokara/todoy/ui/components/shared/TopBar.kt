package com.ikanoshiokara.todoy.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ikanoshiokara.todoy.R
import com.ikanoshiokara.todoy.ui.theme.TodoyTheme

@Composable
fun MainTopBar(
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
        MainTopBar()
    }
}
