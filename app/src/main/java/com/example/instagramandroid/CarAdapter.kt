package com.example.instagramandroid

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CarAdapter(
    private val list: List<Car>,
    private val likeClick: (Car,Int) -> Unit,
    private val itemClick: (Car) -> Unit
) : RecyclerView.Adapter<CarHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarHolder =
        CarHolder.create(parent,likeClick, itemClick)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CarHolder, position: Int) = holder.bind(list[position])
}