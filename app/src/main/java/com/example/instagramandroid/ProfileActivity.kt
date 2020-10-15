package com.example.instagramandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profile_activity.startAnimation(AnimationUtils.loadAnimation(this,R.anim.trance))

        val nameCar = findViewById<TextView>(R.id.tv_name_car_profile)
        val countryCar = findViewById<TextView>(R.id.tv_country_car_profile)
        val id  = intent.getSerializableExtra("id")
        val photoCar = findViewById<ImageView>(R.id.img_photo_profile)
        val bioCar = findViewById<TextView>(R.id.tv_text_car_profile)

        val listCars:ArrayList<Car> = ListCars.getCars()
        for (i in 0 until listCars.size){
            if (listCars[i].id == id){
                nameCar.text = listCars[i].name
                countryCar.text = listCars[i].country
                photoCar.setImageResource(listCars[i].photoProfile)
                bioCar.text = listCars[i].bio
                break
            }
        }

    }



}