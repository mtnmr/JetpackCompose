package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.TodoDatabase
import com.example.todoapp.data.TodoRepository

class MyApplication:Application() {
    val database : TodoDatabase by lazy {
        TodoDatabase.getDatabase(this)
    }

    val repository :TodoRepository by lazy {
        TodoRepository(database.todoDao())
    }
}
