package com.siddhesh.myapplication.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
@Dao
interface ListDao {
    @Insert
    fun InserItem(listEntity: ListEntity)

    @Delete
    fun DeleteItem(listEntity: ListEntity)

    @Query("SELECT*FROM list WHERE item_Id=(SELECT(MAX(item_Id))FROM list)")
    fun fetchMaxId():ListEntity

    @Query("SELECT*FROM list WHERE data_type=1")
    fun fetchDailyist():List<ListEntity>

    @Query("SELECT*FROM list WHERE data_type=2")
    fun fetchWeeklylist():List<ListEntity>

    @Query("SELECT*FROM list WHERE data_type=3")
    fun fetchMonthlylist():List<ListEntity>

    @Query("SELECT*FROM list WHERE data_type=4")
    fun fetchImportantlist():List<ListEntity>

    @Query("SELECT*FROM list WHERE data_type=5")
    fun fetchDreamlist():List<ListEntity>

}