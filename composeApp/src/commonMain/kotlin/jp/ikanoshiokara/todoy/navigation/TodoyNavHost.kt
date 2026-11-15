package jp.ikanoshiokara.todoy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.ikanoshiokara.todoy.ui.home.HomeScreen
import kotlinx.serialization.Serializable

/** 画面のルート定義。画面を増やすときはここに追加する。 */
sealed interface Route {
    @Serializable
    data object Home : Route
}

@Composable
fun TodoyNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.Home) {
        composable<Route.Home> {
            HomeScreen()
        }
    }
}
