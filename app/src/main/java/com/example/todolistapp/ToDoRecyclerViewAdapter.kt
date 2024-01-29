package com.example.todolistapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.databinding.ActivityAddTodoBinding
import com.example.todolistapp.databinding.ItemTodoBinding
import com.example.todolistapp.db.ToDoEntity

// 어댑터 객체를 생성할 때 todoList를 인수로 받아줌
class ToDoRecyclerViewAdapter (private val todoList: ArrayList<ToDoEntity>, private val listener: OnItemLongClickListener) :  // OnItemLongClickListener인터페이스 구현체 넘겨주기
    //  RecyclerView.Adapter<MyViewHolder클래스>를 상속 => 뷰홀더 패턴
    RecyclerView.Adapter<ToDoRecyclerViewAdapter.MyViewHolder>() {

        // MyViewHolder 클래스 생성
        inner class MyViewHolder(binding : ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {
            val tv_importance = binding.tvImportance
            val tv_title = binding.tvTitle
            val root = binding.root
        }

    // MyViewHolder 객체 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // item_todo.xml 뷰 바인딩 객체 생성
        val binding: ItemTodoBinding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    // 받아온 데이터를 MyViewHolder객체에 어떻게 넣어줄지 결정
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val todoData = todoList[position]
        // 중요도에 따라 색상 변경
        when (todoData.importance) {
            1 -> {
                holder.tv_importance.setBackgroundResource(R.color.red)
            }
            2 -> {
                holder.tv_importance.setBackgroundResource(R.color.yellow)
            }
            3 -> {
                holder.tv_importance.setBackgroundResource(R.color.green)
            }
        }
        // 중요도에 따라 중요도 텍스트 변경
        holder.tv_importance.text = todoData.importance.toString()
        // 할 일의 제목 변경
        holder.tv_title.text = todoData.title

        // 할 일이 길게 클릭되었을 때 리스너 함수 실행
        holder.root.setOnClickListener {  // holder.root => 한 아이템뷰의 루트 레이아웃
            listener.onLongClick(position)
            false
        }
    }

    // 데이터가 몇 개인지 반환
    override fun getItemCount(): Int {
        return  todoList.size
    }
}