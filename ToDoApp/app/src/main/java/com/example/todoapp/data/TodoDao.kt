package com.example.todoapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Delete
    suspend fun deleteTodoItem(item:TodoItem)

    @Insert
    suspend fun insertTodoItem(item: TodoItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTodoItem(item: TodoItem)

    @Query("SELECT * FROM todoitem")
    fun getTodoItems(): Flow<List<TodoItem>>
}