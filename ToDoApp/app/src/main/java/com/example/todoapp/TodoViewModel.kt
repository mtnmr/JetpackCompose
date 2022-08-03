package com.example.todoapp

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.TodoItem
import com.example.todoapp.data.TodoRepository

class TodoViewModel(private val repository: TodoRepository):ViewModel() {
    private var _todoList = mutableStateListOf<TodoItem>()
//    private var _todoList = getSampleList().toMutableStateList()
    val todoList:List<TodoItem>
        get() = _todoList


    fun changeIsChecked(item:TodoItem, checked: Boolean){
//        val position = todoList.indexOf(item)
//        val newItem = TodoItem(todoText = item.todoText, isChecked = checked)
//        _todoList[position] = newItem
    }

    fun deleteItem(item: TodoItem){
//        _todoList.remove(item)
    }

    fun addItem(text:String){
//        val newItem = TodoItem(todoText = text)
//        _todoList.add(newItem)
    }

}

fun getSampleList() = List(30) { i -> TodoItem(i, "Task # $i") }

class TodoViewModelFactory(private val repository: TodoRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TodoViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(repository) as T
        }
        throw (IllegalArgumentException("Unknown ViewModel Class"))
    }
}
