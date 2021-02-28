package com.example.instagramandroid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramandroid.R
import com.example.instagramandroid.CityHolder
import com.example.instagramandroid.City

class CityAdapter(
    private val cities : ArrayList<City>,
    private val click: (City) -> Unit
) : RecyclerView.Adapter<CityHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder =
        CityHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false),
            click
        )

    override fun getItemCount(): Int =
        cities.size

    override fun onBindViewHolder(holder: CityHolder, position: Int) =
        holder.bind(cities[position])
}