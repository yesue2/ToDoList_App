package com.example.todolistapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ToDoEntity::class), version = 1)  // 조건 1
abstract class AppDatabase : RoomDatabase() {  // 조건 2
    abstract fun getTodoDao() : ToDoDao  // 조건 3

    companion object {
        val databaseName = "db_todo"  // 데이터베이스 명
        var appDatabase : AppDatabase? = null

        fun getInstance(context : Context) : AppDatabase? {  // 싱글턴 패턴 함수 구현
            if (appDatabase == null) {
                // appDatabase가 null일 때 객체 생성
                appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, databaseName).build()
            }
            // null이 아니면 기존 객체 반환
            return appDatabase
        }
    }
}