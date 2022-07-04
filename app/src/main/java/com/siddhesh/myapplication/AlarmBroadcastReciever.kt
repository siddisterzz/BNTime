package com.siddhesh.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.siddhesh.myapplication.Activity.SnoozeActivity

class AlarmBroadcastReciever:BroadcastReceiver(){
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(p0: Context?, p1: Intent?) {
        val Id=p1?.getIntExtra("Id",0)
        val intent= Intent(p0,SnoozeActivity::class.java)
        intent.putExtra("Id",Id)
        intent.putExtra("tag",p1?.getStringExtra("tag"))
        p0?.startActivity(intent)

    }
}
