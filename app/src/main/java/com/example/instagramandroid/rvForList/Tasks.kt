package com.example.instagramandroid.rvForList

import com.example.instagramandroid.R

object Tasks {

    var tasks  = arrayListOf(
        Task(1,"Сходить на 1 пару", "Не пойду", R.drawable.ic_delete),
        Task(2,"Сходить на 2 пару", "Не пойду",R.drawable.ic_delete),
        Task(3,"Сходить на 3 пару", "Не пойду",R.drawable.ic_delete),
        Task(4,"Сходить на Андроид", "О, кайф, схожу",R.drawable.ic_delete)
    )

    fun cloneData() : ArrayList<Task> {
        val result = ArrayList<Task>()
        for(task: Task in tasks) {
            result.add(task.clone())
        }
        return result
    }

    fun findMoreId(): Int {
        var id = 0
        for (task : Task in tasks)
            if (task.id>id) id = task.id

        return ++id
    }


}