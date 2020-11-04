package com.example.instagramandroid.rvForCard

data class Card(
    val id : Int,
    val icon : Int,
    val name : String,
    val photos : List<Int>,
    var iconLike : Int,
    val iconComment : Int,
    var countLike : Int,
    var textCard : String
)