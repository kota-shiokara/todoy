package com.ikanoshiokara.todoy.ui.components.top

import android.util.Log
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun TopFab() {
    FloatingActionButton(onClick = {
        // TODO
        Log.d("Todoy", "FAB Click")
    }) {
        Icon(Icons.Filled.Add, "Add")
    }
}