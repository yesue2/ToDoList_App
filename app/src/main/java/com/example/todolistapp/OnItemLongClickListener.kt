package com.example.todolistapp

interface OnItemLongClickListener {
    // 길게 클릭되었을 때 실행
    fun onLongClick(position: Int)
}