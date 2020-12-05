package com.example.instagramandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

class SongHolder(
    override val containerView: View,
    private val itemClick: (Song) -> Unit

) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private val nameSong = itemView.findViewById<TextView>(R.id.tv_name_song)
    private val authorSong = itemView.findViewById<TextView>(R.id.tv_author)
    private val image = itemView.findViewById<ImageView>(R.id.img_photo_song)


    private var song: Song? = null

    init {
        itemView.setOnClickListener {
            song?.also(itemClick)
        }
    }


    fun bind(song: Song) {
        with(song) {
            nameSong.text = name
            authorSong.text = author
            image.setImageResource(photo)

        }
        itemView.setOnClickListener {
            itemClick(song)
        }

    }

    companion object {

        fun create(parent: ViewGroup, itemClick: (Song) -> Unit) =
            SongHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false),
                itemClick
            )
    }


}