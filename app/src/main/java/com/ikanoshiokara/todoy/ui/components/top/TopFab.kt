package com.ikanoshiokara.todoy.ui.components.top

import android.util.Log
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.ikanoshiokara.todoy.ui.pages.NavItem

@Composable
fun TopFab(navController: NavController) {
    FloatingActionButton(onClick = {
        // TODO
        navController.navigate(NavItem.AddTaskPage.name)
        Log.d("Todoy", "FAB Click")
    }) {
        Icon(Icons.Filled.Add, "Add")
    }
}