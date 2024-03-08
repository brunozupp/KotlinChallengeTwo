package com.novelitech.kotlinchallengetwo.pages.todolist

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.novelitech.kotlinchallengetwo.models.ActionsTodoList
import com.novelitech.kotlinchallengetwo.ui.components.AppButton
import com.novelitech.kotlinchallengetwo.ui.components.AppItemTodo
import com.novelitech.kotlinchallengetwo.ui.components.AppScaffold
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListPage() {

    val viewModel = viewModel<TodoListViewModel>()
    val items = viewModel.items.collectAsState()
    val actions = viewModel.actions.collectAsState(ActionsTodoList.INITIAL)

    var todoField by remember {
        mutableStateOf("")
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    suspend fun showSnackbar(message: String) {
        snackbarHostState.showSnackbar(
            message = message,
            withDismissAction = true,
        )
    }

    LaunchedEffect(key1 = actions) {

        viewModel.actions.collectLatest {
            when(actions.value) {
                ActionsTodoList.INITIAL -> Log.d("TodoListViewModel", "INITIAL VALUE")
                ActionsTodoList.SHOW_LESS_EQUAL_TO_THREE -> showSnackbar("SHOW_LESS_EQUAL_TO_THREE")
                ActionsTodoList.SHOW_MORE_THAN_THREE -> showSnackbar("SHOW_MORE_THAN_THREE")
            }
        }
    }

    AppScaffold(
        textTopBar = "TodoList",
        snackbarHostState = snackbarHostState,
    ) { innerPadding, snackbarHostState ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
               TextField(
                   modifier = Modifier
                       .weight(1f),
                   value = todoField,
                   onValueChange = { todoField = it },
                   label = {
                       Text("Item to be done")
                   }
               )
               AppButton(
                   text = "Add",
                   onClick = {
                       viewModel.addItem(todoField)

                       todoField = ""
                   },
                   color = 0xFF9932d1,
               )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(items.value) { item ->
                    AppItemTodo(
                        item = item,
                        onCheck = {
                            viewModel.toggleItem(item.id)
                        },
                        onDelete = {
                            viewModel.removeItem(item.id)
                        }
                    )
                }
            }
        }
    }
}

