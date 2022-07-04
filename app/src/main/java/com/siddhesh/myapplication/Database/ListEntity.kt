package com.siddhesh.myapplication.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "list")
data class ListEntity(
    @PrimaryKey val item_Id: Int,
    @ColumnInfo(name = "data_type") val type: Int,
    @ColumnInfo(name = "work") val work: String,
    @ColumnInfo(name = "data_description") val description: String,
    @ColumnInfo(name = "status") var status: Int
)
