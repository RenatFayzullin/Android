package com.example.instagramandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var flag = true

        edit.setOnClickListener {
            if(flag) {
                edit.text = "Применить изменения"
                change_status.visibility = View.VISIBLE
                infa3.visibility = View.INVISIBLE
            }
            else {
                edit.text = "Редактировать профиль"
                infa3.text = change_status.text
                change_status.visibility = View.INVISIBLE
                infa3.visibility = View.VISIBLE
            }
            flag = !flag
        }
    }

}