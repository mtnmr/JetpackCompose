package com.example.todoapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.data.TodoItem
import com.example.todoapp.ui.theme.ToDoAppTheme

@Composable
fun MainScreen(viewModel: TodoViewModel = viewModel()){
    Column {
        AddTodoScreen(viewModel)

        TodoScreen(viewModel)
    }
}


@Composable
fun AddTodoScreen(viewModel: TodoViewModel){
    var text by remember { mutableStateOf("")}

    Row(modifier = Modifier.padding(8.dp)) {
        TextField(
            value = text,
            onValueChange = {text = it },
            label = { Text(text = "ToDo") },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        )

        Button(
            onClick = {
                viewModel.addItem(text)
                text = "" },
            modifier = Modifier.align(CenterVertically)
        ) {
            Text(text = "Add")
        }
        
    }
}

@Preview
@Composable
fun AddTodoScreenPreview(){
    Surface(color = Color.White) {
        AddTodoScreen(TodoViewModel())
    }
}

//item keyをidに変更予定
@Composable
fun TodoScreen(viewModel: TodoViewModel) {
    LazyColumn(){
        items(items = viewModel.todoList, key = {item -> item.todoText }){ item ->
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
        MainScreen()
    }
}