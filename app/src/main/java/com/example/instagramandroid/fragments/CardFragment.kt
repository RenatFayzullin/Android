package com.example.instagramandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagramandroid.R
import com.example.instagramandroid.rvForCard.Card
import com.example.instagramandroid.rvForCard.CardAdapter
import com.example.instagramandroid.rvForCard.ListCard
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_fragment.*

class CardFragment : Fragment() {

    private var adapter: CardAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.card_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CardAdapter(
            ListCard.getCard()
        ) { card: Card, i: Int ->
            if (card.iconLike == R.drawable.ic_likes_border) {
                card.iconLike = R.drawable.ic_like_activity_post
                card.countLike++
            } else {
                card.iconLike = R.drawable.ic_likes_border
                card.countLike--
            }
            cards_recycler.adapter?.notifyItemChanged(i)
        }

        cards_recycler.adapter = adapter
    }
}