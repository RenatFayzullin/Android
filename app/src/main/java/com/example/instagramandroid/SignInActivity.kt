package com.example.instagramandroid

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        sp=getSharedPreferences("app", Context.MODE_PRIVATE)

        enter.setOnClickListener {
            if (enter.alpha==1F){
                if (email.text.toString()==sp.getString("Login",null)&&password.text.toString()==sp.getString("Password",null)){
                    sp.edit().putBoolean("LOGGED",true).apply()
                    startActivity(Intent(this,MainActivity::class.java).apply {
                        putExtra("name",sp.getString("Login",null))
                    })
                }
                else Toast.makeText(this, "Неправильные данные", Toast.LENGTH_SHORT).show()
            }
            else Toast.makeText(this, "Поля незаполнены", Toast.LENGTH_SHORT).show()
        }



        email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                changeAlpha()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                changeAlpha()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })





    }
    private fun changeAlpha(){
        email
        if (email.text.isNotEmpty()&&password.text.isNotEmpty()){
            enter.alpha=1F
        }
        else enter.alpha=0.6F
    }

}