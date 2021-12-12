package com.ikanoshiokara.todoy

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ikanoshiokara.todoy.data.model.Task
import com.ikanoshiokara.todoy.ui.components.MainTopBar
import com.ikanoshiokara.todoy.ui.components.top.TopFab
import com.ikanoshiokara.todoy.ui.pages.AddScreen
import com.ikanoshiokara.todoy.ui.pages.TopScreen
import com.ikanoshiokara.todoy.ui.theme.TodoyTheme

@Composable
fun Screen(modifier: Modifier) {
    var tasks by remember { mutableStateOf(mutableListOf<Task>()) }
    val navController = rememberNavController()
    Surface(
        color = MaterialTheme.colors.background,
        modifier = modifier
    ) {
        Scaffold(
            topBar = { MainTopBar() }
        ){
            NavHost(navController = navController, startDestination = "main"){
                composable("main"){
                    TopScreen(navController = navController, tasks = tasks)
                }
                composable("add"){
                    AddScreen(navController = navController, tasks = tasks)
                }
            }
        }
    }

}
