package com.novelitech.kotlinchallengetwo.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.novelitech.kotlinchallengetwo.models.TodoItemModel
import java.time.format.DateTimeFormatter

@Composable
fun AppItemTodo(
    item: TodoItemModel,
    onDelete: () -> Unit,
    onCheck: () -> Unit,
) {

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    Box(
        modifier = Modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = item.text)
                Text(
                    text = item.createdOn.format(formatter),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Checkbox(
                checked = item.done,
                onCheckedChange = {
                    onCheck()
                }
            )
            Icon(
                modifier = Modifier.clickable { onDelete() },
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete"
            )
        }
    }
}