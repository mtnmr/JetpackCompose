package com.example.todoapp.data

import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) {

    suspend fun deleteTodoItem(item: TodoItem){
        todoDao.deleteTodoItem(item)
    }

    suspend fun insertTodoItem(item: TodoItem){
        todoDao.insertTodoItem(item)
    }

    suspend fun updateTodoItem(item: TodoItem){
        todoDao.updateTodoItem(item)
    }

    fun getTodoItems(): Flow<List<TodoItem>>{
        return todoDao.getTodoItems()
    }
}