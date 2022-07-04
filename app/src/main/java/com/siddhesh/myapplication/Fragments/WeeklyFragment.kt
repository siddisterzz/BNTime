package com.siddhesh.myapplication.Fragments

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.siddhesh.myapplication.Adapter.RecyclerAdapter
import com.siddhesh.myapplication.Database.ListDatabase
import com.siddhesh.myapplication.Database.ListEntity
import com.siddhesh.myapplication.R

class WeeklyFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var recyclerAdapter: RecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weekly, container, false)
        recyclerView=view.findViewById(R.id.recyclerView)
        layoutManager= LinearLayoutManager(activity)
        val weeklyList=RetrieveWeeklyFragment(activity as Context).execute().get()
        if (weeklyList!=null && activity!=null) {
            recyclerAdapter = RecyclerAdapter(activity as Context, weeklyList)
            recyclerView.adapter = recyclerAdapter
            recyclerView.layoutManager = layoutManager
        }
        else{
            Toast.makeText(activity as Context,"No item was added to Weekly",Toast.LENGTH_SHORT).show()
        }
        return view

    }
    class RetrieveWeeklyFragment(val context: Context):AsyncTask<Void,Void,List<ListEntity>>(){
        val db= Room.databaseBuilder(context,ListDatabase::class.java,"list-db").build()
        override fun doInBackground(vararg p0: Void?): List<ListEntity> {
            val list=db.listDao().fetchWeeklylist()
            db.close()
            return list
        }
    }
}