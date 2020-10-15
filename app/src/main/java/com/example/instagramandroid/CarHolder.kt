package com.example.instagramandroid


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

class CarHolder(
    override val containerView: View,
    private val itemClick: (Car) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private val nameCar = itemView.findViewById<TextView>(R.id.tv_name_car)
    private val countryCar = itemView.findViewById<TextView>(R.id.tv_country)
    private val image = itemView.findViewById<ImageView>(R.id.img_photo_car)

    private var car: Car? = null

    init {
        itemView.setOnClickListener {
            car?.also(itemClick)
        }
    }

    fun bind(car: Car) {
        this.car = car
        with(car) {
            nameCar.text = name
            countryCar.text = country
            image.setImageResource(photo)
        }
    }

    companion object {

        fun create(parent: ViewGroup, itemClick: (Car) -> Unit) =
            CarHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false),
                itemClick
            )
    }


}