package com.ikanoshiokara.todoy.ui.pages

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ikanoshiokara.todoy.data.model.Task
import com.ikanoshiokara.todoy.R
import com.ikanoshiokara.todoy.ui.components.MainTopBar
import com.ikanoshiokara.todoy.ui.theme.TodoyTheme

@Composable
fun AddScreen(navController: NavController, tasks: MutableList<Task>) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        topBar = { MainTopBar() }
    ) {
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
                    tasks.add(Task(0, title, description))
                    navController.navigate(NavItem.MainPage.name)
                }
            ) {
                Text(stringResource(id = R.string.add_page_input_submit_button))
            }
        }
    }
}
