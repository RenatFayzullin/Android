package com.example.instagramandroid.rvForList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramandroid.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list.*


class TaskHolder(
    override val containerView: View,
    private val deleteClick: (Int) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {


    fun bind(task: Task) {
        with(task) {
            tv_title.text = title
            tv_deck.text = decs
            img_delete.setImageResource(ic_delete)
        }
        img_delete.setOnClickListener {
            deleteClick(layoutPosition)
        }

    }


    companion object {
        fun create(parent: ViewGroup, deleteClick: (Int) -> Unit): TaskHolder =
            TaskHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false),
                deleteClick
            )
    }

}