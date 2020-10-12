package com.example.instagramandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imgCat:ImageView = findViewById(R.id.image_cat)
        val imgDog:ImageView = findViewById(R.id.image_dog)
        val imgCar:ImageView = findViewById(R.id.image_car)
        val imgKfu:ImageView = findViewById(R.id.image_kfu)
        val imgTiger:ImageView = findViewById(R.id.image_tiger)

        var icLast : View? = null

        var imgLast : ImageView? = null




        back.setOnClickListener{
            text.visibility = View.VISIBLE
            imgLast?.visibility = View.GONE
            icLast?.isSelected = false
            Toast.makeText(this, "Выберите иконку", Toast.LENGTH_SHORT).show()
        }


        ic_cat.setOnClickListener{
            icLast?.isSelected = false
            it.isSelected = true
            icLast = it
            text.visibility = View.GONE

            imgLast?.visibility  = View.GONE
            imgCat.visibility = View.VISIBLE
            imgLast = imgCat

            imgCat.startAnimation(AnimationUtils.loadAnimation(this,R.anim.scale))
        }

        ic_dog.setOnClickListener{
            icLast?.isSelected = false
            it.isSelected = true
            icLast = it
            text.visibility = View.GONE

            imgLast?.visibility  = View.GONE
            imgDog.visibility = View.VISIBLE
            imgLast = imgDog
            imgDog.startAnimation(AnimationUtils.loadAnimation(this,R.anim.alpha))
        }

        ic_car.setOnClickListener{
            icLast?.isSelected = false
            it.isSelected = true
            icLast = it
            text.visibility = View.GONE

            imgLast?.visibility  = View.GONE
            imgCar.visibility = View.VISIBLE
            imgLast = imgCar
            imgCar.startAnimation(AnimationUtils.loadAnimation(this,R.anim.combo2))
        }

        ic_kfu.setOnClickListener{
            icLast?.isSelected = false
            it.isSelected = true
            icLast = it
            text.visibility = View.GONE

            imgLast?.visibility  = View.GONE
            imgKfu.visibility = View.VISIBLE
            imgLast = imgKfu
            imgKfu.startAnimation(AnimationUtils.loadAnimation(this,R.anim.trans))
        }

        ic_tiger.setOnClickListener{
            icLast?.isSelected = false
            it.isSelected = true
            icLast = it
            text.visibility = View.GONE

            imgLast?.visibility  = View.GONE
            imgTiger.visibility = View.VISIBLE
            imgLast = imgTiger
            imgTiger.startAnimation(AnimationUtils.loadAnimation(this,R.anim.combo))
        }
    }
}