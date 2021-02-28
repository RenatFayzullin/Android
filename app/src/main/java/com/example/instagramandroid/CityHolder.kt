package com.example.instagramandroid

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramandroid.R
import com.example.instagramandroid.City

class CityHolder(
    containerView: View,
    private val click: (City) -> Unit
) : RecyclerView.ViewHolder(containerView) {
    var cityName: TextView = itemView.findViewById(R.id.city_name)
    var cityTemp: TextView = itemView.findViewById(R.id.city_temp)

    fun bind(city : City) {
        cityName.text = city.name
        cityTemp.text = "${city.main?.temp?.toInt()}°"
        getColorText(city)

        itemView.setOnClickListener {
            click(city)
        }
    }

    private fun getColorText(city: City){
        when (city.main?.temp!!){
            in -100.0..-20.0 ->
                cityTemp.setTextColor(Color.parseColor("#3700B3"))
            in -20.0..-10.0 ->
                cityTemp.setTextColor(Color.parseColor("#496DF1"))
            in -10.0..0.0 ->
                cityTemp.setTextColor(Color.parseColor("#16F11A"))
            in 0.0..10.0 ->
                cityTemp.setTextColor(Color.parseColor("#F1E616"))
            in 10.0..20.0 ->
                cityTemp.setTextColor(Color.parseColor("#EF9C0B"))
            in 20.0..100.0 ->
                cityTemp.setTextColor(Color.parseColor("#EF0B0B"))
        }

    }
}