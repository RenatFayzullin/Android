package com.example.instagramandroid.rvForCard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramandroid.R
import kotlinx.android.synthetic.main.item_photo.view.*

class ViewPagerAdapter(
    private val listPhoto:List<Int>
) : RecyclerView.Adapter<Pager>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager =
        Pager(LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false))

    override fun onBindViewHolder(holder: Pager, position: Int) = holder.itemView.run {
        img_photo.setImageResource(listPhoto[position])
        indicator.text=(position+1).toString().plus(" photo")
    }

    override fun getItemCount(): Int = listPhoto.size

}
class Pager(itemView: View) : RecyclerView.ViewHolder(itemView)