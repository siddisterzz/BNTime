package com.siddhesh.myapplication.Activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.*
import androidx.room.Room
import com.siddhesh.myapplication.Adapter.RecyclerAdapter
import com.siddhesh.myapplication.AlarmBroadcastReciever
import com.siddhesh.myapplication.Database.ListDatabase
import com.siddhesh.myapplication.Database.ListEntity
import com.siddhesh.myapplication.R
import java.security.Provider
import java.util.*

class DescriptionActivity : AppCompatActivity() {
    lateinit var etWork: EditText
    lateinit var etDescription: EditText
    lateinit var btnDaily: Button
    lateinit var btnWeekly: Button
    lateinit var btnMonthly: Button
    lateinit var btnImportant: Button
    lateinit var btnDream: Button
    lateinit var btnDone: Button
    lateinit var item: ListEntity
    lateinit var llRemind: LinearLayout
    lateinit var llTimer: LinearLayout
    lateinit var llDate: LinearLayout
    lateinit var imgRemind: ImageView
    lateinit var etHour: EditText
    lateinit var etMin: EditText
    lateinit var etMon: EditText
    lateinit var etDay: EditText
    lateinit var txtYear: TextView
    lateinit var btnAmPm: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        etWork = findViewById(R.id.etWork)
        etDescription = findViewById(R.id.etDescription)
        btnDaily = findViewById(R.id.btnDaily)
        btnWeekly = findViewById(R.id.btnWeekly)
        btnMonthly = findViewById(R.id.btnMonthly)
        btnImportant = findViewById(R.id.btnImportant)
        btnDream = findViewById(R.id.btnDream)
        btnDone = findViewById(R.id.Done)
        llRemind = findViewById(R.id.llReminder)
        llDate = findViewById(R.id.llDate)
        llTimer = findViewById(R.id.llTimer)
        imgRemind = findViewById(R.id.btnRemind)
        etHour = findViewById(R.id.etHour)
        etMin = findViewById(R.id.etMin)
        etMon = findViewById(R.id.etMon)
        etDay = findViewById(R.id.etDay)
        txtYear = findViewById(R.id.txtYear)
        btnAmPm = findViewById(R.id.btnAmPm)
        var type = 0
        btnDaily.setOnClickListener {
            type = 1
            btnDaily.setBackgroundResource(R.color.colorPrimary)
            btnWeekly.setBackgroundResource(R.color.White)
            btnMonthly.setBackgroundResource(R.color.White)
            btnImportant.setBackgroundResource(R.color.White)
            btnDream.setBackgroundResource(R.color.White)
            llRemind.visibility = View.GONE
            llTimer.visibility = View.GONE
            llDate.visibility = View.GONE
        }
        btnWeekly.setOnClickListener {
            type = 2
            btnDaily.setBackgroundResource(R.color.White)
            btnWeekly.setBackgroundResource(R.color.colorPrimary)
            btnMonthly.setBackgroundResource(R.color.White)
            btnImportant.setBackgroundResource(R.color.White)
            btnDream.setBackgroundResource(R.color.White)
            llRemind.visibility = View.GONE
            llTimer.visibility = View.GONE
            llDate.visibility = View.GONE
        }
        btnMonthly.setOnClickListener {
            type = 3
            btnDaily.setBackgroundResource(R.color.White)
            btnWeekly.setBackgroundResource(R.color.White)
            btnMonthly.setBackgroundResource(R.color.colorPrimary)
            btnImportant.setBackgroundResource(R.color.White)
            btnDream.setBackgroundResource(R.color.White)
            llRemind.visibility = View.GONE
            llTimer.visibility = View.GONE
            llDate.visibility = View.GONE
        }
        var imgStatus = true
        btnImportant.setOnClickListener {
            type = 4
            btnDaily.setBackgroundResource(R.color.White)
            btnWeekly.setBackgroundResource(R.color.White)
            btnMonthly.setBackgroundResource(R.color.White)
            btnImportant.setBackgroundResource(R.color.colorPrimary)
            btnDream.setBackgroundResource(R.color.White)
            llRemind.visibility = View.VISIBLE
            imgRemind.setImageResource(R.drawable.ic_work_left)
            imgStatus = true
        }
        imgRemind.setOnClickListener {
            if (imgStatus) {
                imgRemind.setImageResource(R.drawable.ic_work_done)
                llTimer.visibility = View.VISIBLE
                llDate.visibility = View.VISIBLE
                imgStatus = false
                txtYear.text = Calendar.getInstance().get(Calendar.YEAR).toString()
            } else {
                imgStatus = true
                imgRemind.setImageResource(R.drawable.ic_work_left)
                llTimer.visibility = View.GONE
                llDate.visibility = View.GONE
            }
        }

        btnDream.setOnClickListener {
            type = 5
            btnDaily.setBackgroundResource(R.color.White)
            btnWeekly.setBackgroundResource(R.color.White)
            btnMonthly.setBackgroundResource(R.color.White)
            btnImportant.setBackgroundResource(R.color.White)
            btnDream.setBackgroundResource(R.color.colorPrimary)
            llRemind.visibility = View.GONE
            llTimer.visibility = View.GONE
            llDate.visibility = View.GONE
        }
        var ss= true
        btnAmPm.setOnClickListener {
            if (ss){
                btnAmPm.text="PM"
                ss=false
            }
            else{
                btnAmPm.text="AM"
                ss=true
            }
        }
        btnDone.setOnClickListener {
            val Item: ListEntity? =
                RetrieveMaxIdItem(
                    this@DescriptionActivity
                ).execute().get()
            var Id = 0
            if (Item != null) {
                Id = Item.item_Id
                println("this is $Id")
            }
            if (type == 0) {
                Toast.makeText(
                    this@DescriptionActivity,
                    "Please select a type to proceed.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (type == 4) {
                if ((etDay.text.isEmpty() || etHour.text.isEmpty() || etMon.text.isEmpty() || etMin.text.isEmpty()) && imgStatus==false) {
                    Toast.makeText(
                        this@DescriptionActivity,
                        "Please fill all the fields",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (imgStatus) {
                    item = ListEntity(
                        Id + 1,
                        type,
                        etWork.text.toString(),
                        etDescription.text.toString(), 1
                    )
                    val async = DBAsyncTask(
                        this@DescriptionActivity,
                        item,
                        1
                    ).execute()
                    val result = async.get()
                    if (result) {
                        val intent = Intent(
                            this@DescriptionActivity,
                            MainActivity::class.java
                        )
                        intent.putExtra("type", type)
                        startActivity(intent)
                    }
                } else {
                    if (type == 4 && !imgStatus && etDay.text.toString()
                            .toInt() > 0 && etDay.text.toString()
                            .toInt() < 32 && etHour.text.toString()
                            .toInt() > 0 && etHour.text.toString()
                            .toInt() < 13 && etMin.text.toString()
                            .toInt() >= 0 && etMin.text.toString()
                            .toInt() < 60 && etMon.text.toString()
                            .toInt() > 0 && etMon.text.toString().toInt() < 13
                    ) {//alarm set
                        val calendar = Calendar.getInstance()
                        calendar.set(Calendar.YEAR,Calendar.getInstance().get(Calendar.YEAR))
                        calendar.set(
                            Calendar.MONTH,
                            etMon.text.toString().toInt() - 1
                        )//month is specified from 0 to 11
                        calendar.set(Calendar.DAY_OF_MONTH, etDay.text.toString().toInt())
                        calendar.set(Calendar.HOUR_OF_DAY, etHour.text.toString().toInt())
                        calendar.set(Calendar.MINUTE, etMin.text.toString().toInt())
                        calendar.set(Calendar.SECOND, 0)
                        calendar.set(Calendar.MILLISECOND, 0)
                        if (ss==false){
                            calendar.set(Calendar.AM_PM,Calendar.PM)
                        }
                        else{
                         calendar.set(Calendar.AM_PM,Calendar.AM)
                        }
                        val i = Intent(this@DescriptionActivity, AlarmBroadcastReciever::class.java)
                        i.putExtra("Id", Id + 1)
                        i.putExtra("tag",etWork.text.toString())
                        val pi: PendingIntent =
                            PendingIntent.getBroadcast(this@DescriptionActivity, Id + 1, i, 0)
                        val am: AlarmManager =
                            getSystemService(Context.ALARM_SERVICE) as AlarmManager
                        println(calendar.timeInMillis)
                        am.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pi)
                        Toast.makeText(this@DescriptionActivity, "Alarm Set", Toast.LENGTH_SHORT)
                            .show()
                        item = ListEntity(
                            Id + 1,
                            type,
                            etWork.text.toString(),
                            etDescription.text.toString(), 1
                        )
                        val async = DBAsyncTask(
                            this@DescriptionActivity,
                            item,
                            1
                        ).execute()
                        val result = async.get()
                        if (result) {
                            val intent = Intent(
                                this@DescriptionActivity,
                                MainActivity::class.java
                            )
                            intent.putExtra("type", type)
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(
                            this@DescriptionActivity,
                            "Please fill all the credentials properly",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            else {
                item = ListEntity(
                    Id + 1,
                    type,
                    etWork.text.toString(),
                    etDescription.text.toString(), 1
                )
                val async = RecyclerAdapter.DBAsyncTask(
                    this@DescriptionActivity,
                    item,
                    1
                ).execute()
                val result = async.get()
                if (result) {
                    val intent = Intent(
                        this@DescriptionActivity,
                        MainActivity::class.java
                    )
                    intent.putExtra("type", type)
                    startActivity(intent)
                }
            }
        }
    }
}

class DBAsyncTask(val context: Context, val listEntity: ListEntity, val Mode: Int) :
    AsyncTask<Void, Void, Boolean>() {
    val db = Room.databaseBuilder(context, ListDatabase::class.java, "list-db").build()
    override fun doInBackground(vararg p0: Void?): Boolean {
        if (Mode == 1) {
            db.listDao().InserItem(listEntity)
            db.close()
            return true
        }
        return false
    }
}

class RetrieveMaxIdItem(context: Context) : AsyncTask<Void, Void, ListEntity>() {
    val db = Room.databaseBuilder(context, ListDatabase::class.java, "list-db").build()
    override fun doInBackground(vararg p0: Void?): ListEntity {
        val max = db.listDao().fetchMaxId()
        db.close()
        return max
    }
}
