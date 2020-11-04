package com.example.instagramandroid.rvForList

data class Task(
    val id: Int,
    val title: String,
    val decs: String,
    val ic_delete: Int
){
    fun clone() : Task =
        Task(this.id,this.title,this.decs,this.ic_delete)
}