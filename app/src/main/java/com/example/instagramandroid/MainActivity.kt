package com.example.instagramandroid

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btTime: Button = findViewById(R.id.bt_time)
        var btDate: Button = findViewById(R.id.bt_date)
        var next: Button = findViewById(R.id.next)

        btTime.setOnClickListener {
            var vkRenat: Uri = Uri.parse("https://vk.com/renat_f14")
            var intent = Intent(Intent.ACTION_VIEW, vkRenat)
            startActivity(intent)
        }
        btDate.setOnClickListener {
            var massageText = "Привет"
            var intent2 = Intent(Intent.ACTION_SEND)
            intent2.type = "text/plane"
            intent2.putExtra(Intent.EXTRA_TEXT, massageText)
            var chosenIntent = Intent.createChooser(intent2, "Отправить сообщение")
            startActivity(chosenIntent)

        }

        next.setOnClickListener {
            var textIntent: EditText = findViewById(R.id.text_intent)

            var intent4 = Intent(Intent.ACTION_SEND)
            intent4.type = "text/plane"
            intent4.putExtra("name", textIntent.text.toString())
            var chosenIntent1 = Intent.createChooser(intent4, "Выберете приложение")
            startActivityForResult(chosenIntent1, 1)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var name: String? = data?.getStringExtra("name")
        if (name != null) {
            Toast.makeText(this, "Ответ пользователя - $name", Toast.LENGTH_SHORT).show()
        }

    }


}