package jp.ikanoshiokara.todoy.components.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import jp.ikanoshiokara.todoy.data.model.Task

interface BottomSheetItem {
    operator fun invoke(state: State)

    sealed class State {
        object Hidden : State()
        data class AddTask(val addTask: (Task) -> Unit) : State()

        fun isExpanded(): Boolean { // 戻るボタンの時BottomSheetの状態を見る
            return this !is Hidden
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetContent(
    sheetState: ModalBottomSheetState,
    sheetItem: BottomSheetItem.State
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction = 0.9f)
    ) {
        when (sheetItem) {
            is BottomSheetItem.State.Hidden -> {
                LaunchedEffect(key1 = null) {
                    sheetState.hide()
                }
            }
            is BottomSheetItem.State.AddTask -> {
                AddTaskPage(sheetItem.addTask)
            }
        }
    }
}
