package com.example.todolistapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistapp.databinding.ActivityMainBinding
import com.example.todolistapp.db.AppDatabase
import com.example.todolistapp.db.ToDoDao
import com.example.todolistapp.db.ToDoEntity

class MainActivity : AppCompatActivity(), OnItemLongClickListener {
    private lateinit var binding: ActivityMainBinding

    private lateinit var db : AppDatabase
    private lateinit var toDoDao: ToDoDao
    private lateinit var todoList: ArrayList<ToDoEntity>
    private lateinit var adapter: ToDoRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent)
        }

        // DB 인스턴스를 가져오고 DB작업을 할 수 있는 DAO를 가져옴
        db = AppDatabase.getInstance(this)!!
        toDoDao = db.getTodoDao()

        getAllTodoList()
    }

    private fun getAllTodoList() {
        // 백그라운드에서 실행
        Thread {
            todoList = ArrayList(toDoDao.getAll())
            setRecyclerView()
        }.start()
    }

    private fun setRecyclerView() {
        // UI 작업이므로 UI 스레드에서 실행
        runOnUiThread {
            adapter = ToDoRecyclerViewAdapter(todoList, this)  // 어댑터 객체 할당
            // 리사이클러뷰 어댑터로 위에서 만든 어댑터 설정
            binding.recyclerView.adapter = adapter
            // 레이아웃 매니저 설정
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
        }
    }

    // 액티비티가 멈췄다가 다시 시작되었을 때 실행되는 함수(생명주기 관련)
    override fun onRestart() {
        super.onRestart()
        getAllTodoList()
    }

    override fun onLongClick(position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("할 일 삭제")
        builder.setMessage("정말 삭제하시겠습니다?")
        builder.setNegativeButton("취소", null)
        builder.setPositiveButton("네",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    deleteTodo(position)
                }
            })
        builder.show()
    }

    private fun deleteTodo(position: Int) {
        Thread {
            toDoDao.deleteTodo(todoList[position])  // DB에서 삭제
            todoList.removeAt(position)  // 리스트에서 삭제
            runOnUiThread {   // UI 관련 작업은 UI 스레드에
                adapter.notifyDataSetChanged()  // 어댑터에게 데이터가 바뀌었음을 알려줌 => 리사이클러뷰가 그에 맞춰 자동 업데이트
                Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }
}