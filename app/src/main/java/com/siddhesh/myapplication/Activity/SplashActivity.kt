package com.siddhesh.myapplication.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.siddhesh.myapplication.R

class SplashActivity : AppCompatActivity() {
    lateinit var imgLogo: ImageView
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        imgLogo = findViewById(R.id.imgLogo)
        sharedPreferences =
            getSharedPreferences(getString(R.string.my_preference_file), Context.MODE_PRIVATE)
        imgLogo.animate().alpha(0f).setDuration(2000)
        StartAct()
    }

    fun StartAct() {
        Handler().postDelayed({
            if (sharedPreferences.getString("password", "") == "") {
                val intent = Intent(this@SplashActivity, PasswordActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }, 2500)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}