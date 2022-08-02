package com.example.todoapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.data.TodoItem
import com.example.todoapp.ui.theme.ToDoAppTheme

@Composable
fun TodoScreen(viewModel: TodoViewModel = viewModel()) {
    LazyColumn(){
        items(items = viewModel.todoList, key = {item -> item.id }){ item ->
            TodoItemView(
                todoText = item.todoText,
                todoChecked = item.isChecked,
                checkedChange = { checked -> viewModel.changeIsChecked(item, checked) },
                deleteClicked = { viewModel.deleteItem(item) }
            )
        }
    }
}

@Composable
fun TodoItemView(
    todoText:String,
    todoChecked:Boolean,
    checkedChange: (Boolean) -> Unit,
    deleteClicked: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = todoChecked,
            onCheckedChange = checkedChange
        )
        
        Text(
            text = todoText,
            modifier = Modifier.weight(1f)
        )

        IconButton(
            onClick = deleteClicked
        ) {
            Icon(Icons.Filled.Delete, contentDescription = "item delete")
        }
    }
}

@Preview
@Composable
fun TodoItemPreview(){
    Surface(color = Color.White) {
        TodoScreen()
    }
}