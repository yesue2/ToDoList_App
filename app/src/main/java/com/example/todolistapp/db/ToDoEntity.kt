package com.example.todolistapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity  // 구성요소 알려주는 어노테이션 필수
data class ToDoEntity (
    // 기본키 => 각 정보를 식별하는 값
    // autoGenerate = true => id가 자동으로 1씩 증가되며 저장
    @PrimaryKey(autoGenerate = true) var id : Int? = null,
    @ColumnInfo(name = "title") val title : String,
    @ColumnInfo(name = "importance") val importance : Int
)