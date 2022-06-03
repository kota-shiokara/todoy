package com.ikanoshiokara.todoy.components.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.ikanoshiokara.todoy.LocalBottomSheetItem
import com.ikanoshiokara.todoy.data.model.Task
import com.ikanoshiokara.todoy.R

@Composable
fun AddTaskPage(
    addTask: (Task) -> Unit
) {
    var title by remember { mutableStateOf(TextFieldValue()) }
    var description by remember { mutableStateOf(TextFieldValue()) }
    val sheetItem = LocalBottomSheetItem.current

    Scaffold {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(id = R.string.add_page_input_title)) }
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(stringResource(id = R.string.add_page_input_description)) }
            )
            Button(
                modifier = Modifier.padding(10.dp),
                onClick = {
                    addTask(Task.newTask(title = title.text, description = description.text))
                    sheetItem(BottomSheetItem.State.Hidden)
                }
            ) {
                Text(stringResource(id = R.string.add_page_input_submit_button))
            }
        }
    }
}
