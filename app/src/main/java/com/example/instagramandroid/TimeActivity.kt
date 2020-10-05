package com.example.instagramandroid

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_time.*
import org.w3c.dom.Text
import java.lang.Thread.sleep
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class TimeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time)

        var sdf = SimpleDateFormat("HH:mm:ss")
        var time: String = sdf.format(Date(System.currentTimeMillis()))

        var tvTime: TextView = findViewById(R.id.time_1)
        tvTime.text = time

        var name: String? = intent.extras?.getString("name")
        que.text = name


        Thread {
            while (true) {
                sleep(1000)
                runOnUiThread {
                    time = sdf.format(Date(System.currentTimeMillis()))
                    tvTime.text = time

                }
            }
        }.start()

        yes.setOnClickListener {
            var intent6 = Intent()
            intent6.putExtra("name", yes.text.toString())
            setResult(Activity.RESULT_OK, intent6)
            finish()
        }

        no.setOnClickListener {
            var intent6 = Intent()
            intent6.putExtra("name", no.text.toString())
            setResult(Activity.RESULT_OK, intent6)
            finish()
        }
        answer_send.setOnClickListener {
            var intent7 = Intent()
            intent7.putExtra("name", answer.text.toString())
            setResult(Activity.RESULT_OK, intent7)
            finish()
        }


    }


}