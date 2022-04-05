package com.ikanoshiokara.todoy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ikanoshiokara.todoy.data.model.Task
import com.ikanoshiokara.todoy.ui.components.MainTopBar
import com.ikanoshiokara.todoy.ui.pages.AddTaskPage
import com.ikanoshiokara.todoy.ui.pages.NavItem
import com.ikanoshiokara.todoy.ui.pages.MainPage
import com.ikanoshiokara.todoy.ui.theme.TodoyTheme

val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("No Current NavController")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoyTheme {
                var tasks by remember { mutableStateOf(mutableListOf<Task>()) }
                val navController = rememberNavController()
                CompositionLocalProvider(LocalNavController provides navController) {
                    Scaffold(
                        // TODO: ボトムバーとか...いる？
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            NavHost(navController = LocalNavController.current, startDestination = NavItem.MainPage.name){
                                composable(NavItem.MainPage.name){
                                    MainPage(tasks = tasks)
                                }
                                composable(NavItem.AddTaskPage.name){
                                    AddTaskPage(tasks = tasks)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}