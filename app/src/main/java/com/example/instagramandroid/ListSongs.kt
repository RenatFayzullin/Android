package com.example.instagramandroid

object ListSongs {

    fun getSong(): ArrayList<Song> = arrayListOf(
        Song(
            0,
            "I Got Love",
            "MiYagi",
            R.raw.i_got_love,
            R.drawable.i_got_love_photo_mini,
            R.drawable.i_got_love_photo
        ),
        Song(
            1,
            "КАДИЛЛАК",
            "MORGENSHTERN",
            R.raw.kadilac,
            R.drawable.cadillac_mini,
            R.drawable.cadillac
        ),
        Song(
            2,
            "Мне пох",
            "Клава-Кока",
            R.raw.mne_pox,
            R.drawable.mne_pox_mini,
            R.drawable.mne_pox)
    )

    var currentSong = 0
    var maxSong = getSong().size - 1

    fun getNext(): Song {
        if (currentSong == maxSong) {
            currentSong = 0
        } else currentSong++
        return getCurrentSong()
    }

    fun getPrev(): Song {
        if (currentSong == 0) {
            currentSong = maxSong
        } else currentSong--
        return getCurrentSong()
    }

    fun getCurrentSong():Song = getSong()[currentSong]
}