package jp.ikanoshiokara.todoy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.kiwi.navigationcompose.typed.composable
import com.kiwi.navigationcompose.typed.createRoutePattern
import dagger.hilt.android.AndroidEntryPoint
import jp.ikanoshiokara.todoy.ui.NavItem
import jp.ikanoshiokara.todoy.ui.screen.home.HomeScreen
import jp.ikanoshiokara.todoy.ui.theme.TodoyTheme
import kotlinx.serialization.ExperimentalSerializationApi

val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("No Current NavController")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoyTheme {
                App()
            }
        }
    }
}

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun App() {
    val navController = rememberNavController()

    // バケツリレーしなくて済むのいいねというやつ
    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                NavHost(
                    navController = LocalNavController.current,
                    startDestination = createRoutePattern<NavItem.Home>()
                ) {
                    composable<NavItem.Home> {
                        HomeScreen()
                    }
                }
            }
        }
    }
}
