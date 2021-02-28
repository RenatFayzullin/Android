package com.example.instagramandroid

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_weather_info.*
import kotlinx.coroutines.launch
import java.util.*

class WeatherInfo : AppCompatActivity() {
    var cityId: Int = -1

    private val api = ApiFactory.weatherApi

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat")
    private val currentTime = SimpleDateFormat("H").format(Calendar.getInstance().time).toInt()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_info)

        when (currentTime){
            22,23,24, in 0..5 ->
                main.setBackgroundResource(R.drawable.night)
            in 5..11 ->
                main.setBackgroundResource(R.drawable.morning)
            in 11..18 ->
                main.setBackgroundResource(R.drawable.daytime)
            else ->
                main.setBackgroundResource(R.drawable.evening)
        }


        cityId = intent.getIntExtra("city_id", -1)

        lifecycleScope.launch {
            val city = api.getWeather(cityId)
            tv_name.text = city.name
            tv_temp.text = "${city.main?.temp?.toInt()}°"
            tv_deck.text = city.weather[0].description
            sunrise.text = getDate(city.sys.sunrise)
            sunset.text = getDate(city.sys.sunset)
            wind.text = city.getWind().plus(" ").plus(city.wind.speed.toInt()).plus(" м/с")
            humidity.text = city.main?.humidity.toString().plus(" %")

        }

        bt_back.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java))
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat")
    private fun getDate(time: Long): String? {
        val date = Date(time * 1000L)
        val sdf = SimpleDateFormat("HH:mm")
        sdf.timeZone = TimeZone.getTimeZone("GMT+3")
        return sdf.format(date)
    }

}