package com.example.ecommerceapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

            var splash_icon: ImageView = findViewById(R.id.splash_icon)
            splash_icon.animate().rotation(3600f).setDuration(4000)

            Timer().schedule(object : TimerTask() {
                override fun run() {

                    var intent: Intent = Intent(this@SplashActivity, FirstPage::class.java)

                    startActivity(intent)
                    finish()
                }

            }, 4000)

        }
    }


