package com.ikanoshiokara.todoy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ikanoshiokara.todoy.data.model.Task
import com.ikanoshiokara.todoy.ui.pages.AddTaskPage
import com.ikanoshiokara.todoy.ui.pages.BottomSheetContent
import com.ikanoshiokara.todoy.ui.pages.BottomSheetItem
import com.ikanoshiokara.todoy.ui.pages.NavItem
import com.ikanoshiokara.todoy.ui.pages.MainPage
import com.ikanoshiokara.todoy.ui.theme.TodoyTheme

val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("No Current NavController")
}

val LocalBottomSheetItem = compositionLocalOf<BottomSheetItem> {
    error("No Current Bottom Sheet State")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoyTheme {
                MainContent()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent() {
    val navController = rememberNavController()

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
//        ModalBottomSheetLayout(
//        ) {
            var tasks by remember { mutableStateOf(mutableListOf<Task>()) }
            Scaffold(
                // TODO: ボトムバーとか...いる？
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    NavHost(
                        navController = LocalNavController.current,
                        startDestination = NavItem.MainPage.name
                    ) {
                        composable(NavItem.MainPage.name) {
                            MainPage(tasks = tasks)
                        }
                        composable(NavItem.AddTaskPage.name) {
                            AddTaskPage(tasks = tasks)
                        }
                    }
                }
            }
//        }
    }
}

