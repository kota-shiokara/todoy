package com.ikanoshiokara.todoy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ikanoshiokara.todoy.data.model.Task
import com.ikanoshiokara.todoy.ui.components.MainTopBar
import com.ikanoshiokara.todoy.ui.pages.AddScreen
import com.ikanoshiokara.todoy.ui.pages.NavItem
import com.ikanoshiokara.todoy.ui.pages.MainPage
import com.ikanoshiokara.todoy.ui.theme.TodoyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoyTheme {
                var tasks by remember { mutableStateOf(mutableListOf<Task>()) }
                val navController = rememberNavController()
                Scaffold(
                    topBar = { MainTopBar() }
                ){
                    NavHost(navController = navController, startDestination = NavItem.MainPage.name){
                        composable(NavItem.MainPage.name){
                            MainPage(navController = navController, tasks = tasks)
                        }
                        composable(NavItem.AddTaskPage.name){
                            AddScreen(navController = navController, tasks = tasks)
                        }
                    }
                }
            }
        }
    }
}