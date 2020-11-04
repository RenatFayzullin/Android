package com.example.instagramandroid.rvForCard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramandroid.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_rec.*

class Holder(
    override val containerView: View,
    private val likeClick: (Card, Int) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {


    fun bind(card: Card) {
        with(card) {
            ic_img.setImageResource(icon)
            name_card.text = name
            viewPager2.adapter = ViewPagerAdapter(photos)
            likes_post.setImageResource(iconLike)
            comments_post.setImageResource(iconComment)
            count_likes.text = countLike.toString()
            text_card.text = textCard
        }
        likes_post.setOnClickListener {
            likeClick(card, layoutPosition)
        }
    }

    companion object {
        fun create(parent: ViewGroup, likeClick: (Card, Int) -> Unit):Holder =
            Holder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_rec, parent, false),
                likeClick
            )
    }

}