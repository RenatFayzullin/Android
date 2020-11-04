package com.example.instagramandroid.rvForCard

import com.example.instagramandroid.R

object ListCard {

    fun getCard():ArrayList<Card> = arrayListOf(
        Card(1,
            R.drawable.ic_profile_border,"renat_f14",
            ListPhoto.getCars(),
            R.drawable.ic_likes_border,
            R.drawable.ic_comments_border,665,"renat_f14 : Мои малышки"),
        Card(1,
            R.drawable.ic_profile_border,"renat_f14",
            ListPhoto.getCats(),
            R.drawable.ic_likes_border,
            R.drawable.ic_comments_border,10890,"renat_f14 : Мои киски"),
        Card(1,
            R.drawable.ic_profile_border,"renat_f14",
            ListPhoto.getKzn(),
            R.drawable.ic_likes_border,
            R.drawable.ic_comments_border,2020,"renat_f14 : Мой любимый город")
    )
}