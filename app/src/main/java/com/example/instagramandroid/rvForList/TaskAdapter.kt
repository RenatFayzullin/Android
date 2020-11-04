package com.example.instagramandroid.rvForList

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    var list: List<Task>,
    private val deleteClick: (Int) -> Unit
) : RecyclerView.Adapter<TaskHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder =
        TaskHolder.create(parent,deleteClick)

    override fun onBindViewHolder(holder: TaskHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    fun updateDS(newList : ArrayList<Task>){
        if (newList != list){
            val callback = DiffCallback(list,newList)
            val result = DiffUtil.calculateDiff(callback,true)
            result.dispatchUpdatesTo(this)

            list = newList
            Tasks.tasks = newList
        }
    }

    fun onItemDelete(item: Int){
        val arr = Tasks.cloneData()
        arr.removeAt(item)
        updateDS(arr)
    }
}
