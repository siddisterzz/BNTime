package com.siddhesh.myapplication.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.siddhesh.myapplication.Fragments.*
import com.siddhesh.myapplication.R
import java.time.Month

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var navigationView: NavigationView
    lateinit var frameLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.DrawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        navigationView = findViewById(R.id.navigationView)
        frameLayout = findViewById(R.id.frameLayout)
        var previousItem: MenuItem? = null
        setUpToolbar()
        makeupHamburger()
        Daily()
        SavedItem()
        navigationView.setNavigationItemSelectedListener {
            if (previousItem != null) {
                previousItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousItem = it
            when (it.itemId) {
                R.id.daily -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                            DailyFragment()
                        )
                        .commit()
                    supportActionBar?.title = "Daily List"
                    drawerLayout.closeDrawers()
                }
                R.id.weekly -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, WeeklyFragment())
                        .commit()
                    supportActionBar?.title = "Weekly List"
                    drawerLayout.closeDrawers()
                }
                R.id.monthly -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, MonthlyFragment())
                        .commit()
                    supportActionBar?.title = "Monthly List"
                    drawerLayout.closeDrawers()
                }
                R.id.Important -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, ImportantFragment())
                        .commit()
                    supportActionBar?.title = "Important List"
                    drawerLayout.closeDrawers()
                }
                R.id.Dream -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, DreamFragment())
                        .commit()
                    supportActionBar?.title = "Dream List"
                    drawerLayout.closeDrawers()
                }
            }
            return@setNavigationItemSelectedListener true
        }

        }
    //Sets the toolbar title and displays the home button(in form of back arrow)
    fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    //Truns the arrow to hamburger icon
    fun makeupHamburger(){
        val actionBarDrawerToggle=ActionBarDrawerToggle(this@MainActivity,drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }
    /*as soon as the user enters password transaction occurs and DailyFragment is set to the frame,
    the checked item is also marked by default daily...check if you have enabled the checkablbe behavior as single in menu XML file.
     */

    fun Daily(){
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frameLayout,
                DailyFragment()
            )
            .commit()
        supportActionBar?.title="Daily List"
        navigationView.setCheckedItem(R.id.daily)
    }
        fun SavedItem(){
            if (intent.getIntExtra("type",0)!=0){
                val type=intent.getIntExtra("type",0)
                when(type){
                    1 ->{
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.frameLayout,
                                DailyFragment()
                            )
                            .commit()
                        supportActionBar?.title="Daily List"
                        navigationView.setCheckedItem(R.id.daily)
                    }
                    2->{
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout,WeeklyFragment())
                            .commit()
                        supportActionBar?.title="Weekly List"
                        navigationView.setCheckedItem(R.id.weekly)
                    }
                    3->{
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout,MonthlyFragment())
                            .commit()
                        supportActionBar?.title="Monthly List"
                        navigationView.setCheckedItem(R.id.monthly)
                    }
                    4->{
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout,ImportantFragment())
                            .commit()
                        supportActionBar?.title="Important List"
                        navigationView.setCheckedItem(R.id.Important)
                    }
                    5->{
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout,DreamFragment())
                            .commit()
                        supportActionBar?.title="Dream List"
                        navigationView.setCheckedItem(R.id.Dream)
                    }

                }
            }
    }
    //Makes the home button functional
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id= item.itemId
        if (id==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        else if(id== R.id.Add){
            val intent=Intent(this@MainActivity,
                DescriptionActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
//inflates the + sign
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu,menu)
        return true
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}