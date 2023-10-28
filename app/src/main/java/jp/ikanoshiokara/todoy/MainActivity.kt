package jp.ikanoshiokara.todoy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.kiwi.navigationcompose.typed.composable
import com.kiwi.navigationcompose.typed.createRoutePattern
import dagger.hilt.android.AndroidEntryPoint
import jp.ikanoshiokara.todoy.components.pages.BottomSheetContent
import jp.ikanoshiokara.todoy.components.pages.BottomSheetItem
import jp.ikanoshiokara.todoy.components.pages.MainPage
import jp.ikanoshiokara.todoy.ui.NavItem
import jp.ikanoshiokara.todoy.components.theme.TodoyTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("No Current NavController")
}

val LocalBottomSheetItem = compositionLocalOf<BottomSheetItem> {
    error("No Current Bottom Sheet State")
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

@OptIn(ExperimentalMaterialApi::class, ExperimentalSerializationApi::class)
@Composable
fun App() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    // BottomSheet関係
    var bottomSheetItem by remember { mutableStateOf<BottomSheetItem.State>(BottomSheetItem.State.Hidden) }
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val bottomSheetItemSetter = remember {
        object : BottomSheetItem {
            override fun invoke(state: BottomSheetItem.State) {
                bottomSheetItem = state
                scope.launch {
                    bottomSheetState.hide()
                }
            }
        }
    }

    // BottomSheetが出てるかどうかで戻るボタンの挙動が変わる
    navController.enableOnBackPressed(!bottomSheetItem.isExpanded())
    BackHandler(
        enabled = bottomSheetItem.isExpanded(),
        onBack = {
            bottomSheetItemSetter.invoke(BottomSheetItem.State.Hidden)
        }
    )

    // バケツリレーしなくて済むのいいねというやつ
    CompositionLocalProvider(
        LocalNavController provides navController,
        LocalBottomSheetItem provides bottomSheetItemSetter
    ) {
        ModalBottomSheetLayout(
            sheetState = bottomSheetState,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetContent = {
                BottomSheetContent(
                    sheetState = bottomSheetState,
                    sheetItem = bottomSheetItem
                )
            }
        ) {
            Scaffold { paddingValues ->
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
                            MainPage()
                        }
                    }
                }
            }
        }
    }
}
