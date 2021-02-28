package com.example.instagramandroid

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : AppCompatActivity() {


    private var rvAdapter: CityAdapter? = null

    private var wayLatitude = 55.7887
    private var wayLongitude = 49.1221


    private val api = ApiFactory.weatherApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        lifecycleScope.launch {
            val nearCitiesResponse =
                api.getNearCitiesInfo(wayLatitude.toInt(), wayLongitude.toInt())
            val nearCities = nearCitiesResponse.list

            rvAdapter = CityAdapter(nearCities as ArrayList<City>) {
                startWeatherActivity(it)

            }

            rv_cities.adapter = rvAdapter

        }



        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onQueryTextSubmit(query: String?): Boolean {
                lifecycleScope.launch {
                    try {
                        val city = api.getWeather(search_view.query.toString())
                        startWeatherActivity(city)
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@MainActivity,
                            "Введитет корректное название города",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })

    }

    private fun startWeatherActivity(currentCity: City) {
        startActivity(
            Intent(
                this,
                WeatherInfo::class.java
            ).apply {
                putExtra("city_id", currentCity.id)
            })
    }


}