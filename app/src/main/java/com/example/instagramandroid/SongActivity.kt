package com.example.instagramandroid

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_song_profile.*
import java.lang.Exception

class SongActivity : AppCompatActivity() {

    private var mPlayer: MediaPlayer? = null
    private var currentSong: Int? = null
    private val listSongs: ArrayList<Song> = ListSongs.getSong()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_profile)

        val id = intent.getSerializableExtra("id")
        var position: Int = id as Int


        for (i in 0 until listSongs.size) {
            if (listSongs[i].id == id) {
                tv_name_song.text = listSongs[i].name
                tv_author.text = listSongs[i].author
                img_photo_song.setImageResource(listSongs[i].photoProfile)
                break
            }
        }


        controlSound(listSongs[id].rawSong)

        next.setOnClickListener {
            if (position + 1 == listSongs.size) {
                position = 0
            } else position += 1
            nextSound(position)
        }

        prev.setOnClickListener {
            if (position == 0) {
                position = listSongs.size - 1
            } else position -= 1
            prevSound(position)
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        mPlayer?.stop()
        mPlayer?.reset()
        mPlayer?.release()
        mPlayer = null
    }

    private fun prevSound(position: Int) {
        if (mPlayer != null) {
            mPlayer?.stop()
            mPlayer?.reset()
            mPlayer?.release()
            mPlayer = MediaPlayer.create(this, listSongs[position].rawSong)
            initialiseSeekBar()
        }
        mPlayer?.start()
        tv_name_song.text = listSongs[position].name
        tv_author.text = listSongs[position].author
        img_photo_song.setImageResource(listSongs[position].photoProfile)
    }

    private fun nextSound(position: Int) {

        if (mPlayer != null) {
            mPlayer?.stop()
            mPlayer?.reset()
            mPlayer?.release()
            mPlayer = MediaPlayer.create(this, listSongs[position].rawSong)
            initialiseSeekBar()
        }
        mPlayer?.start()
        tv_name_song.text = listSongs[position].name
        tv_author.text = listSongs[position].author
        img_photo_song.setImageResource(listSongs[position].photoProfile)
    }

    private fun controlSound(idSong: Int) {

        play.setOnClickListener {
            if (mPlayer == null) {
                mPlayer = MediaPlayer.create(this, idSong)
                initialiseSeekBar()
            }
            mPlayer?.start()
        }

        pause.setOnClickListener {
            if (mPlayer != null) mPlayer?.pause()
        }


        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, user: Boolean) {
                if (user) {
                    mPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    }

    private fun initialiseSeekBar() {
        seekBar.max = mPlayer!!.duration

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    seekBar.progress = mPlayer!!.currentPosition
                    handler.postDelayed(this, 1000)
                } catch (e: Exception) {
                    seekBar.progress = 0
                }
            }
        }, 0)
    }

}
