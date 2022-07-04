package com.siddhesh.myapplication.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.siddhesh.myapplication.R

class LoginActivity : AppCompatActivity() {
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        sharedPreferences =
            getSharedPreferences(getString(R.string.my_preference_file), Context.MODE_PRIVATE)
        btnLogin.setOnClickListener {
            if (etPassword.text.toString() == sharedPreferences.getString("password", "")) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this@LoginActivity,"Wrong Password",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}