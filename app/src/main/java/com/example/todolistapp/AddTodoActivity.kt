package com.example.todolistapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolistapp.databinding.ActivityAddTodoBinding
import com.example.todolistapp.db.AppDatabase
import com.example.todolistapp.db.ToDoDao
import com.example.todolistapp.db.ToDoEntity

class AddTodoActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddTodoBinding
    lateinit var db : AppDatabase
    lateinit var todoDao: ToDoDao  // ToDoDao인터페이스 => insert, delete, query 기능 제공
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()

        binding.btnCompletion.setOnClickListener {
            insertTodo()
        }
    }

    private fun insertTodo() {
        val todoTitle = binding.editTitle.text.toString()  // 할 일의 제목
        var todoImportance = binding.radioGroup.checkedRadioButtonId  // 할 일의 중요도
        when(todoImportance) {
            R.id.btn_high -> {
                todoImportance = 1
            }
            R.id.btn_middle -> {
                todoImportance = 2
            }
            R.id.btn_low -> {
                todoImportance = 3
            }
            else -> {
                todoImportance = -1
            }
        }

        // 중요도가 선택되지 않았거나, 제목이 작성되지 않았는지 체크
        if (todoImportance == -1 || todoTitle.isBlank()) {
            Toast.makeText(this, "모든 항목을 채워주세요.", Toast.LENGTH_SHORT).show()
        } else {
            // 백그라운드 스레드 실행 => 작업량이 큰 데이터베이스 관련 작업은 반드시 백그라운드 스레드에서 진행
            // 이를 실무에서는 Coroutines, RxJava, RxKotlin과 같은 라이브러리 사용(비동기 처리)
            Thread {
                todoDao.insertTodo(ToDoEntity(null, todoTitle, todoImportance))
                runOnUiThread {
                    Toast.makeText(this, "추가되었습니다.", Toast.LENGTH_SHORT) .show()
                    finish()
                }
            }.start()
        }
    }
}