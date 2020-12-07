package com.example.instagramandroid

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SongAdapter(
    private val list: List<Song>,
    private val itemClick: (Song) -> Unit
) : RecyclerView.Adapter<SongHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder =
        SongHolder.create(parent, itemClick)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SongHolder, position: Int) = holder.bind(list[position])
}