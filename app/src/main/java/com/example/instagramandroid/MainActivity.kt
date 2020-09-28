package com.example.instagramandroid

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var sp:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp=getSharedPreferences("app", Context.MODE_PRIVATE)

        if (!sp.contains("Login")){
            sp.edit().apply {
                putString("Login","renat_f14")
                putString("Password","12345")
                putBoolean("LOGGED",false).apply()
            }
        }

        if (!sp.getBoolean("LOGGED",false)){
            startActivity(Intent(this,SignInActivity::class.java))
        }


        exit.setOnClickListener {
            sp.edit().putBoolean("LOGGED",false).apply()
            startActivity(Intent(this,SignInActivity::class.java))
        }



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