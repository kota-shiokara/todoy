package com.ikanoshiokara.todoy.ui.pages

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable

sealed class BottomSheetItem {
    object AddTask: BottomSheetItem()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetContent(
    sheetState: ModalBottomSheetState,
    sheetItem: BottomSheetItem
) {
    if(!sheetState.isVisible) return
    when(sheetItem) {
        is BottomSheetItem.AddTask -> {

        }
        else -> {
            // TODO: エラーみたいなものを入れようか
        }
    }
}