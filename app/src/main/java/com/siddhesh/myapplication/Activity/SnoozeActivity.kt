package com.siddhesh.myapplication.Activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import com.siddhesh.myapplication.AlarmBroadcastReciever
import com.siddhesh.myapplication.R

class SnoozeActivity : AppCompatActivity() {
    lateinit var btnPressHere: Button
    lateinit var ItemTag:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snooze)
        btnPressHere = findViewById(R.id.btnPressHere)
        ItemTag=findViewById(R.id.ItemTag)
        btnPressHere.animate().translationYBy(1000f)
        ItemTag.animate().translationXBy(1000f)
        ItemTag.text=intent.getStringExtra("tag")
        val Id=intent.getIntExtra("Id",0)
        Handler().postDelayed({
            ItemTag.animate().translationXBy(-1000f)
            btnPressHere.animate().translationYBy(-1000f)
        },1000)
        val mp: MediaPlayer = MediaPlayer.create(this@SnoozeActivity, R.raw.alarm_clock_2015)
        mp.start()
        btnPressHere.setOnClickListener {
            mp.stop()
            val i=Intent(this@SnoozeActivity,AlarmBroadcastReciever::class.java)
            val pi=PendingIntent.getBroadcast(this@SnoozeActivity,Id,i,PendingIntent.FLAG_CANCEL_CURRENT)
            val am: AlarmManager =
                getSystemService(Context.ALARM_SERVICE) as AlarmManager
            am.cancel(pi)
            val intent= Intent(this@SnoozeActivity,MainActivity::class.java)
            intent.putExtra("type",4)
            startActivity(intent)
        }
    }
}