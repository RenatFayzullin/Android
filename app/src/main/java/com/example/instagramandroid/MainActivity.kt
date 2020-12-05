package com.example.instagramandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list_songs.adapter = SongAdapter(
            ListSongs.getSong()
        ) {
            val song = it
            val intent = Intent(this,SongActivity::class.java)
            intent.putExtra("id",song.id)
            startActivity(intent)
        }

        list_songs.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))

    }

}