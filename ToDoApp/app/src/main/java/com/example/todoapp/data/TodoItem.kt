package com.example.todoapp.data

data class TodoItem(
    val id : Int,
    val todoText : String,
    var isChecked : Boolean = false
)

