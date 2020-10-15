package com.example.instagramandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_activity.startAnimation(AnimationUtils.loadAnimation(this,R.anim.trance))

        recyclerView.adapter = CarAdapter(
            ListCars.getCars(),
        { car : Car, i: Int ->
            if(car.like == R.drawable.ic_like){
                car.like = R.drawable.ic_like_active
            }
            else {
                car.like = R.drawable.ic_like
            }
            recyclerView.adapter?.notifyItemChanged(i)

        },
        {
            var car: Car = it
            var intent = Intent(this,ProfileActivity::class.java)
            intent.putExtra("id",car.id)
            startActivity(intent)

        }
        )


        recyclerView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))

        swipe.setOnRefreshListener {
            swipe.isRefreshing = false
        }
    }
}