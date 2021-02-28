package com.example.instagramandroid

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("clouds")
    var clouds: Clouds,
    @SerializedName("coord")
    var coord: Coord,
    @SerializedName("dt")
    var dt: Int?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("main")
    var main: Main?,
    @SerializedName("name")
    var name: String,
    @SerializedName("rain")
    var rain: Any,
    @SerializedName("snow")
    var snow: Snow,
    @SerializedName("sys")
    var sys: Sys,
    @SerializedName("weather")
    var weather: List<Weather>,
    @SerializedName("wind")
    var wind: Wind,
    @SerializedName("cod")
    var cod: Int,
    @SerializedName("timezone")
    var timezone: Int,
    @SerializedName("visibility")
    var visibility: Int
) {

    fun getWind(): String =
        when (wind.deg) {
            in 338..361, in 0..23 -> "с"
            in 23..68 -> "с-в"
            in 68..113 -> "в"
            in 113..158 -> "ю-в"
            in 158..205 -> "ю"
            in 205..248 -> "ю-з"
            in 248..293 -> "з"
            in 293..338 -> "с-з"
            else -> ""
        }

}