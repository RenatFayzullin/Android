package com.example.instagramandroid

import android.media.MediaPlayer

object ListSongs {

    fun getSong():ArrayList<Song> = arrayListOf(
        Song(0,"I Got Love","MiYagi",R.raw.i_got_love,R.drawable.i_got_love_photo_mini,R.drawable.i_got_love_photo),
        Song(1,"КАДИЛЛАК","MORGENSHTERN",R.raw.kadilac,R.drawable.cadillac_mini,R.drawable.cadillac),
        Song(2,"Мне пох","Клава-Кока",R.raw.mne_pox,R.drawable.mne_pox_mini,R.drawable.mne_pox)
    )
}