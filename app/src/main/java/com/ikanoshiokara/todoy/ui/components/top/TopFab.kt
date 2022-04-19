package com.ikanoshiokara.todoy.ui.components.top

import android.util.Log
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.ikanoshiokara.todoy.LocalBottomSheetItem
import com.ikanoshiokara.todoy.ui.pages.BottomSheetItem
import com.ikanoshiokara.todoy.ui.pages.NavItem
import kotlinx.coroutines.launch

@Composable
fun TopFab() {
    val bottomSheet = LocalBottomSheetItem.current
    FloatingActionButton(onClick = {
        // TODO
        bottomSheet(BottomSheetItem.State.AddTask)
        Log.d("Todoy", "FAB Click")
    }) {
        Icon(Icons.Filled.Add, "Add")
    }
}