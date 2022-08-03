package com.example.todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoItem(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val todoText : String,
    var isChecked : Boolean = false
)

