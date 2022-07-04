package com.siddhesh.myapplication.Adapter

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.siddhesh.myapplication.AlarmBroadcastReciever
import com.siddhesh.myapplication.Database.ListDatabase
import com.siddhesh.myapplication.Database.ListEntity
import com.siddhesh.myapplication.R

class RecyclerAdapter(val context: Context, val list: List<ListEntity>) :
    RecyclerView.Adapter<RecyclerAdapter.DailyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return DailyViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val item = list[position]
        holder.txtWork.text = item.work
        holder.txtDescription.text = item.description
        holder.txtDescription.visibility = View.GONE
        when(item.status){
            1->{
                holder.txtStatus.text="Task Incomplete"
                holder.txtStatus.setTextColor(Color.parseColor("#F80808"))
                holder.imgCheck.setImageResource(R.drawable.ic_work_left)
            }
            2->{
                holder.txtStatus.text="Task On Progress"
                holder.txtStatus.setTextColor(Color.parseColor("#6200EE"))
                holder.imgCheck.setImageResource(R.drawable.ic_on_progress)
            }
            3->{
                holder.txtStatus.text="Task Complete"
                holder.txtStatus.setTextColor(Color.parseColor("#18EE34"))
                holder.imgCheck.setImageResource(R.drawable.ic_work_done)
            }
        }
        var s=true
        holder.imgOpenDescription.setOnClickListener {
            if (s){
                holder.txtDescription.visibility = View.VISIBLE
                s=false
            }
            else{
             holder.txtDescription.visibility=View.GONE
                s=true
            }
        }
        holder.imgCheck.setOnClickListener{
            if (item.status == 1) {
                holder.imgCheck.setImageResource(R.drawable.ic_on_progress)
                var result=DBAsyncTask(context,item,2).execute().get()
                item.status = 2
                if (result){
                    result=DBAsyncTask(context,item,1).execute().get()
                    Toast.makeText(context,"List Updated",Toast.LENGTH_SHORT).show()
                }

            } else if (item.status == 2) {
                holder.imgCheck.setImageResource(R.drawable.ic_work_done)
                var result=DBAsyncTask(context,item,2).execute().get()
                item.status = 3

                if (result){
                    result=DBAsyncTask(context,item,1).execute().get()
                    Toast.makeText(context,"List Updated",Toast.LENGTH_SHORT).show()
                }
            } else {
                holder.imgCheck.setImageResource(R.drawable.ic_work_left)
                var result=DBAsyncTask(context,item,2).execute().get()
                item.status = 1
                if (result){
                    result=DBAsyncTask(context,item,1).execute().get()
                    Toast.makeText(context,"List Updated",Toast.LENGTH_SHORT).show()
                }
            }
            when(item.status){
                1->{
                    holder.txtStatus.text="Task Incomplete"
                    holder.txtStatus.setTextColor(Color.parseColor("#F80808"))
                }
                2->{
                    holder.txtStatus.text="Task On Progress"
                    holder.txtStatus.setTextColor(Color.parseColor("#6200EE"))
                }
                3->{
                    holder.txtStatus.text="Task Complete"
                    holder.txtStatus.setTextColor(Color.parseColor("#18EE34"))
                }
            }
        }
        holder.imgDelete.setOnClickListener {
         val async=DBAsyncTask(context,item,2).execute().get()
            if (async) {
                Toast.makeText(context, "Task Deleted", Toast.LENGTH_SHORT).show()
                holder.CardView.animate().alpha(0f).setDuration(500)
                Handler().postDelayed({
                    holder.llWork.visibility = View.GONE
                    holder.llWork2.visibility = View.GONE
                    holder.txtDescription.visibility=View.GONE
                },500)
                val i= Intent(context, AlarmBroadcastReciever::class.java)
                val pi= PendingIntent.getBroadcast(context,item.item_Id,i, PendingIntent.FLAG_CANCEL_CURRENT)
                val am:AlarmManager= context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                am.cancel(pi)
            }
            }
        }

    class DailyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgCheck: ImageView = view.findViewById(R.id.imgCheck)
        val txtWork: TextView = view.findViewById(R.id.txtWork)
        val txtDescription: TextView = view.findViewById(R.id.txtDescription)
        val imgOpenDescription: ImageView = view.findViewById(R.id.imgOpenDescription)
        val txtStatus:TextView=view.findViewById(R.id.txtStatus)
        val imgDelete:ImageView=view.findViewById(R.id.imgDelete)
        val CardView:CardView=view.findViewById(R.id.CardView)
        val llWork:LinearLayout=view.findViewById(R.id.llWork)
        val llWork2:LinearLayout=view.findViewById(R.id.llWork2)
    }
    class DBAsyncTask(val context: Context, val listEntity: ListEntity, val Mode: Int) :
        AsyncTask<Void, Void, Boolean>() {
        val db = Room.databaseBuilder(context, ListDatabase::class.java, "list-db").build()
        override fun doInBackground(vararg p0: Void?): Boolean {
            when(Mode) {
                1->{
                db.listDao().InserItem(listEntity)
                db.close()
                return true
            }
                2->{
                    db.listDao().DeleteItem(listEntity)
                    db.close()
                    return true
                }
            }

            return false
        }
    }
}
