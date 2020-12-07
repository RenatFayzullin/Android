package com.example.instagramandroid

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.instagramandroid.PlayerService.PlayerServiceBinder
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    var playerServiceBinder : PlayerService.PlayerServiceBinder? = null
    private var mediaController: MediaControllerCompat? = null

    private val imgPlay = R.drawable.ic_play
    private val imgPause = R.drawable.ic_pause
    private val callback: MediaControllerCompat.Callback? = null
    private var serviceConnection:ServiceConnection?=null

    private var musicServiceBinder:PlayerService.PlayerServiceBinder?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var playing : Boolean? = null



        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                playerServiceBinder = service as PlayerServiceBinder
                try {
                    mediaController = MediaControllerCompat(
                        this@MainActivity, playerServiceBinder!!.mediaSessionToken
                    )
                    mediaController?.registerCallback(
                        object : MediaControllerCompat.Callback() {
                            override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
                                super.onPlaybackStateChanged(state)
                                if (state == null)
                                    return
                                playing = state.state == PlaybackStateCompat.STATE_PLAYING
                                var skipNext =
                                    state.state == PlaybackStateCompat.STATE_SKIPPING_TO_NEXT
                                var skipPrev =
                                    state.state == PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS
                                var pause = state.state == PlaybackStateCompat.STATE_PAUSED

                                val currentTrack = ListSongs.getCurrentSong()

                                if (playing == true) {
                                    bt_start_pause.setImageResource(imgPause)
                                } else bt_start_pause.setImageResource(imgPlay)

                                if (skipNext|| skipPrev|| playing as Boolean){
                                    tv_name_song_toolbar.text = currentTrack.name
                                    tv_author_toolbar.text = currentTrack.author
                                }
                            }
                            override fun onMetadataChanged(metadata: MediaMetadataCompat?){
                                if (metadata == null)
                                    return
                                seekBar?.max =
                                    metadata.bundle.getLong(MediaMetadataCompat.METADATA_KEY_DURATION)
                                        .toInt()

                                Timer().scheduleAtFixedRate(object : TimerTask() {
                                    override fun run() {
                                        seekBar?.progress =
                                            musicServiceBinder?.getCurrentPosition() ?: 0
                                    }
                                }, 0, 1000)

                                seekBar?.setOnSeekBarChangeListener(object :
                                    SeekBar.OnSeekBarChangeListener {
                                    override fun onProgressChanged(
                                        seekBar: SeekBar?,
                                        progress: Int,
                                        fromUser: Boolean
                                    ) {
                                        if (fromUser)
                                            mediaController?.transportControls?.seekTo(progress.toLong())
                                        if ((seekBar?.max?.minus(1000))!! <= progress)
                                            mediaController?.transportControls?.skipToNext()
                                    }

                                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                                        Log.i("update", "update")
                                    }

                                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                                        Log.i("update", "update")
                                    }
                                })
                            }
                        }
                    )

                } catch (e: RemoteException){
                    mediaController = null
                }
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
                playerServiceBinder = null
                mediaController = null
            }

        }

        bindService(
            Intent(this, PlayerService::class.java),
            serviceConnection as ServiceConnection, BIND_AUTO_CREATE
        )

        bt_start_pause.setOnClickListener {
            if (playing == false){
                mediaController?.transportControls?.play()
            }
            else mediaController?.transportControls?.pause()

        }

        bt_next.setOnClickListener {
            if (mediaController!=null){
                mediaController?.transportControls?.skipToNext()
            }
        }

        bt_prev.setOnClickListener {
            if (mediaController!=null){
                mediaController?.transportControls?.skipToPrevious()
            }
        }





        list_songs.adapter = SongAdapter(
            ListSongs.getSong()
        ) {
            ListSongs.currentSong = it.id
            if (playing==false){
                mediaController?.transportControls?.play()
            }
            else {
                mediaController?.transportControls?.stop();
                mediaController?.transportControls?.play()
            }
        }
        list_songs.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))


    }

    override fun onDestroy() {
        super.onDestroy()
        playerServiceBinder = null
        if (mediaController != null){
            if (callback != null) {
                mediaController?.unregisterCallback(callback)
            }
            mediaController = null
        }
        serviceConnection?.let { unbindService(it) }
    }


}