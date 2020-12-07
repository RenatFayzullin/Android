package com.example.instagramandroid

import android.R
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.media.session.PlaybackState
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.media.session.MediaButtonReceiver
import com.example.instagramandroid.MisicHelper.from


class PlayerService : Service() {


    private val metadataBuilder = MediaMetadataCompat.Builder()
    private var player: MediaPlayer? = null
    private var musicRepository = ListSongs
    private val NOTIFICATION_ID = 102
    private val NOTIFICATION_DEFAULT_CHANNEL_ID = "default_channel"
    private var currentState = PlaybackStateCompat.STATE_STOPPED

    private lateinit var mediaSession: MediaSessionCompat


    private val stateBuilder =
        PlaybackStateCompat.Builder().setActions(
            PlaybackStateCompat.ACTION_PLAY
                    or PlaybackStateCompat.ACTION_STOP
                    or PlaybackStateCompat.ACTION_PAUSE
                    or PlaybackStateCompat.ACTION_PLAY_PAUSE
                    or PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                    or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
        )


    @SuppressLint("WrongConstant")
    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(
                    NOTIFICATION_DEFAULT_CHANNEL_ID,
                    "NOTIFICATION_ID",
                    NotificationManagerCompat.IMPORTANCE_HIGH
                )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }

        mediaSession = MediaSessionCompat(this, "MusicService")
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
        mediaSession.setCallback(mediaSessionCallback)

        val appContext = applicationContext

        val activityIntent = Intent(appContext, MainActivity::class.java)
        mediaSession.setSessionActivity(
            PendingIntent.getActivity(
                appContext,
                0,
                activityIntent,
                Intent.FLAG_ACTIVITY_SINGLE_TOP
            )
        )

        val mediaButtonIntent = Intent(
            Intent.ACTION_MEDIA_BUTTON, null, appContext,
            MediaButtonReceiver::class.java
        )
        mediaSession.setMediaButtonReceiver(
            PendingIntent.getBroadcast(
                appContext,
                0,
                mediaButtonIntent,
                0
            )
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        mediaSession.release()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        MediaButtonReceiver.handleIntent(mediaSession, intent)
        return super.onStartCommand(intent, flags, startId)
    }


    private var mediaSessionCallback = object : MediaSessionCompat.Callback() {
            private var currentRaw: Int? = null

            override fun onPlay() {
                startService(Intent(applicationContext, PlayerService::class.java))
                val track: Song = ListSongs.getCurrentSong()

                updateMetadateFromTrack(track)
                mediaSession.isActive = true
                mediaSession.setPlaybackState(
                    stateBuilder.setState(
                        PlaybackStateCompat.STATE_PLAYING,
                        PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN,
                        1F
                    ).build()
                )

                prepareToPlay(track.rawSong)
                player?.start()
                currentState = PlaybackStateCompat.STATE_PLAYING
                refreshNotificationAndForegroundStatus(currentState)
            }

            override fun onPause() {
                super.onPause()
                player?.pause()
                mediaSession.setPlaybackState(
                    stateBuilder.setState(
                        PlaybackStateCompat.STATE_PAUSED,
                        PlaybackState.PLAYBACK_POSITION_UNKNOWN,
                        1F
                    ).build()
                )
                currentState = PlaybackStateCompat.STATE_PAUSED

                refreshNotificationAndForegroundStatus(currentState)
            }

            override fun onStop() {
                super.onStop()
                player?.stop()

                mediaSession.isActive = false

                mediaSession.setPlaybackState(
                    stateBuilder.setState(
                        PlaybackStateCompat.STATE_STOPPED,
                        PlaybackState.PLAYBACK_POSITION_UNKNOWN,
                        1F
                    ).build()
                )

                currentState = PlaybackStateCompat.STATE_STOPPED;

                refreshNotificationAndForegroundStatus(currentState);
            }

            override fun onSeekTo(pos: Long) {
                player?.seekTo(pos.toInt())
            }

            override fun onSkipToNext() {
                super.onSkipToNext()
                val track: Song = ListSongs.getNext()
                mediaSession.setPlaybackState(
                    stateBuilder.setState(
                        PlaybackStateCompat.STATE_SKIPPING_TO_NEXT,
                        PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1F
                    ).build()
                )
                updateMetadateFromTrack(track)
                prepareToPlay(track.rawSong)
                player?.start()
                currentState = PlaybackStateCompat.STATE_SKIPPING_TO_NEXT

                refreshNotificationAndForegroundStatus(currentState);
            }

            override fun onSkipToPrevious() {
                super.onSkipToPrevious()
                val track: Song = ListSongs.getPrev()
                mediaSession.setPlaybackState(
                    stateBuilder.setState(
                        PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS,
                        PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1F
                    ).build()
                )
                updateMetadateFromTrack(track)
                prepareToPlay(track.rawSong)
                player?.start()
                currentState = PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS

                refreshNotificationAndForegroundStatus(currentState);
            }

            private fun prepareToPlay(raw: Int) {
                if (raw != currentRaw) {
                    currentRaw = raw
                    player?.stop()
                    player?.reset()
                    player?.release()
                    player = MediaPlayer.create(applicationContext, raw)
                }
            }

            private fun updateMetadateFromTrack(track: Song){
                metadataBuilder.putBitmap(MediaMetadataCompat.METADATA_KEY_ART,BitmapFactory.decodeResource(resources,track.photo))
                metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, track.name)
                metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, track.author)
                player?.duration?.toLong()?.let {
                    metadataBuilder.putLong(MediaMetadataCompat.METADATA_KEY_DURATION,
                        it
                    )
                }

                mediaSession.setMetadata(metadataBuilder.build())
            }
        }


    override fun onBind(p0: Intent?): IBinder? {
        return PlayerServiceBinder()
    }

    private fun refreshNotificationAndForegroundStatus(playbackState: Int) {
        when (playbackState) {
            PlaybackStateCompat.STATE_PLAYING -> {
                startForeground(NOTIFICATION_ID, getNotification(playbackState))
            }
            PlaybackStateCompat.STATE_PAUSED -> {
                NotificationManagerCompat.from(this)
                    .notify(NOTIFICATION_ID, getNotification(playbackState)!!)
            }
            PlaybackStateCompat.STATE_SKIPPING_TO_NEXT -> {
                NotificationManagerCompat.from(this)
                    .notify(NOTIFICATION_ID, getNotification(playbackState)!!)
            }
            PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS -> {
                NotificationManagerCompat.from(this)
                    .notify(NOTIFICATION_ID, getNotification(playbackState)!!)
            }
            else -> {
                stopForeground(true)
            }
        }
    }

    private fun getNotification(playbackState: Int): Notification? {
        val builder =
            from(this, mediaSession)
        builder!!.addAction(
            NotificationCompat.Action(
                R.drawable.ic_media_previous,
                "prev",
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    this,
                    PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                )
            )
        )
        Log.i("state", "$playbackState")
        if (playbackState == PlaybackStateCompat.STATE_PLAYING ||
            playbackState == PlaybackStateCompat.STATE_SKIPPING_TO_NEXT ||
            playbackState == PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS
        ) builder.addAction(
            NotificationCompat.Action(
                R.drawable.ic_media_pause,
                "pause",
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    this,
                    PlaybackStateCompat.ACTION_PAUSE
                )
            )
        ) else builder.addAction(
            NotificationCompat.Action(
                R.drawable.ic_media_play,
                "play",
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    this,
                    PlaybackStateCompat.ACTION_PLAY
                )
            )
        )
        builder.addAction(
            NotificationCompat.Action(
                R.drawable.ic_media_next,
                "next",
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    this,
                    PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                )
            )
        )
        builder.setStyle(
            androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(1)
                .setShowCancelButton(true)
                .setCancelButtonIntent(
                    MediaButtonReceiver.buildMediaButtonPendingIntent(
                        this,
                        PlaybackStateCompat.ACTION_STOP
                    )
                )
                .setMediaSession(mediaSession.sessionToken)
        )
        builder.setSmallIcon(R.mipmap.sym_def_app_icon)
        builder.color = ContextCompat.getColor(
            this,
            R.color.white
        )
        builder.setShowWhen(false)
        builder.priority = NotificationCompat.PRIORITY_HIGH
        builder.setOnlyAlertOnce(true)
        builder.setChannelId(NOTIFICATION_DEFAULT_CHANNEL_ID)
        return builder.build()
    }

    open inner class PlayerServiceBinder : Binder() {
        val mediaSessionToken: MediaSessionCompat.Token
            get() = mediaSession.sessionToken
        fun getCurrentPosition(): Int? =
            player?.currentPosition
    }


}
