package com.example.todoapp

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.data.TodoItem

class TodoViewModel:ViewModel() {
    private var _todoList = getSampleList().toMutableStateList()
    val todoList:List<TodoItem>
        get() = _todoList


    fun changeIsChecked(item:TodoItem, checked: Boolean){
        todoList.find { it.id == item.id }?.let {
            it.isChecked = checked
        }
    }

    fun deleteItem(item: TodoItem){
        _todoList.remove(item)
    }
}

fun getSampleList() = List(30) { i -> TodoItem(i, "Task # $i") }