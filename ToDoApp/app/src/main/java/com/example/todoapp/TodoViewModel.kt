package com.example.todoapp

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.data.TodoItem
import com.example.todoapp.data.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository):ViewModel() {
    /*
    private var _todoList = mutableStateListOf<TodoItem>()
//    private var _todoList = getSampleList().toMutableStateList()
    val todoList:List<TodoItem>
        get() = _todoList

     */

    val todoList :LiveData<List<TodoItem>> = repository.getTodoItems().asLiveData()


    fun changeIsChecked(item:TodoItem, checked: Boolean){
        val newItem = TodoItem(id = item.id, todoText = item.todoText, isChecked = checked)
        viewModelScope.launch {
            repository.updateTodoItem(newItem)
        }
    }

    fun deleteItem(item: TodoItem){
        viewModelScope.launch{
            repository.deleteTodoItem(item)
        }
    }

    fun addItem(text:String){
        val newItem = TodoItem(todoText = text)
        viewModelScope.launch {
            repository.insertTodoItem(newItem)
        }
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
