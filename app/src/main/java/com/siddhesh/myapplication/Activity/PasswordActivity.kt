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

class PasswordActivity : AppCompatActivity() {
    lateinit var etPassword:EditText
    lateinit var etConfirmPassword:EditText
    lateinit var btnClickHere:Button
    lateinit var sharedPreferences:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)
        etPassword=findViewById(R.id.etPassword)
        etConfirmPassword=findViewById(R.id.etConfirmPassword)
        btnClickHere=findViewById(R.id.btnClickHere)
        sharedPreferences=getSharedPreferences(getString(R.string.my_preference_file), Context.MODE_PRIVATE)
        btnClickHere.setOnClickListener {
            if (etPassword.text.toString()==etConfirmPassword.text.toString()){
                sharedPreferences.edit().putString("password",etPassword.text.toString()).apply()
                val intent= Intent(this@PasswordActivity,
                    LoginActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this@PasswordActivity,"Confirm password and password must be same.",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}