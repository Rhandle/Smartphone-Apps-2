package com.example.simplenotepad.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "world_table")
data class WorldData(
    @PrimaryKey(autoGenerate = true) val id : Int?,
    @ColumnInfo(name = "title") val title : String?,
    @ColumnInfo(name = "summary") val summary : String?,
    @ColumnInfo(name = "date") val date : String?

) : java.io.Serializable
