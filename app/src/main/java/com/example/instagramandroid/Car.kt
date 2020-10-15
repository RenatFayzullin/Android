package com.example.instagramandroid

data class Car (
    val id : Int,
    val name : String,
    val country : String,
    val photo : Int,
    val photoProfile : Int,
    val bio : String,
    var like : Int
)