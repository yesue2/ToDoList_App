package com.example.todolistapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ToDoDao {
    // 생성한 ToDoEntity에서 모든 데이터를 불러오는 쿼리 함수
    @Query("select * from ToDoEntity")
    fun getAll() : List<ToDoEntity>

    //ToDoEntity 객체를 테이블에 삽입
    @Insert
    fun insertTodo(todo: ToDoEntity)

    // 특정 ToDoEntity 객체를 테이블에서 삭제
    @Delete
    fun deleteTodo(todo: ToDoEntity)
}