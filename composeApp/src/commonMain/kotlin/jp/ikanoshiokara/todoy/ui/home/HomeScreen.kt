package jp.ikanoshiokara.todoy.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.ikanoshiokara.todoy.resources.Res
import jp.ikanoshiokara.todoy.resources.app_name
import jp.ikanoshiokara.todoy.resources.home_add
import jp.ikanoshiokara.todoy.resources.home_delete
import jp.ikanoshiokara.todoy.resources.home_empty
import jp.ikanoshiokara.todoy.resources.home_input_placeholder
import jp.ikanoshiokara.todoy.shared.model.Task
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * 基盤 (DB → Repository → ViewModel → UI) の疎通確認用の最小画面。
 * 本格的な画面設計が決まったら置き換える前提。
 */
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeContent(
        uiState = uiState,
        onAddTask = viewModel::addTask,
        onToggleDone = viewModel::toggleDone,
        onDelete = viewModel::deleteTask,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    uiState: HomeUiState,
    onAddTask: (String) -> Unit,
    onToggleDone: (Task) -> Unit,
    onDelete: (Task) -> Unit,
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(Res.string.app_name)) }) },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            TaskInput(onAddTask = onAddTask)
            when (uiState) {
                HomeUiState.Loading -> {
                    CenteredBox { CircularProgressIndicator() }
                }

                is HomeUiState.Success -> {
                    if (uiState.tasks.isEmpty()) {
                        CenteredBox { Text(stringResource(Res.string.home_empty)) }
                    } else {
                        TaskList(tasks = uiState.tasks, onToggleDone = onToggleDone, onDelete = onDelete)
                    }
                }
            }
        }
    }
}

@Composable
private fun TaskInput(onAddTask: (String) -> Unit) {
    var title by rememberSaveable { mutableStateOf("") }
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier.weight(1f),
            singleLine = true,
            placeholder = { Text(stringResource(Res.string.home_input_placeholder)) },
        )
        TextButton(
            onClick = {
                onAddTask(title)
                title = ""
            },
            enabled = title.isNotBlank(),
        ) {
            Text(stringResource(Res.string.home_add))
        }
    }
}

@Composable
private fun TaskList(
    tasks: List<Task>,
    onToggleDone: (Task) -> Unit,
    onDelete: (Task) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(tasks, key = { it.id }) { task ->
            ListItem(
                headlineContent = {
                    Text(
                        text = task.title,
                        textDecoration = if (task.isDone) TextDecoration.LineThrough else null,
                    )
                },
                leadingContent = {
                    Checkbox(checked = task.isDone, onCheckedChange = { onToggleDone(task) })
                },
                trailingContent = {
                    TextButton(onClick = { onDelete(task) }) {
                        Text(stringResource(Res.string.home_delete))
                    }
                },
            )
            HorizontalDivider()
        }
    }
}

@Composable
private fun CenteredBox(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { content() }
}
