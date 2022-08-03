package com.example.todoapp

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.data.TodoItem

class TodoViewModel:ViewModel() {
    private var _todoList = mutableStateListOf<TodoItem>()
    val todoList:List<TodoItem>
        get() = _todoList


    fun changeIsChecked(item:TodoItem, checked: Boolean){
        val position = todoList.indexOf(item)
        val newItem = TodoItem(todoText = item.todoText, isChecked = checked)
        _todoList[position] = newItem
    }

    fun deleteItem(item: TodoItem){
        _todoList.remove(item)
    }

    fun addItem(text:String){
        val newItem = TodoItem(todoText = text)
        _todoList.add(newItem)
    }

}

fun getSampleList() = List(30) { i -> TodoItem("Task # $i") }