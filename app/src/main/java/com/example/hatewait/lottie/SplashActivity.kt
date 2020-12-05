package com.example.hatewait.lottie

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.hatewait.R
import com.example.hatewait.login.MainActivity

class Splash : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val TIME_OUT: Long = 1000

        Handler().postDelayed(Runnable {
            kotlin.run {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, TIME_OUT)
    }
}