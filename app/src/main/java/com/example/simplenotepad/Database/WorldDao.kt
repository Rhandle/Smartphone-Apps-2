package com.example.simplenotepad.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.simplenotepad.Models.WorldData

@Dao
interface WorldDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wdata : WorldData)

    @Delete
    suspend fun delete(wdata : WorldData)

    @Query("Select * from world_table order by id ASC")
    fun getWorldData() : LiveData<List<WorldData>>

    @Query("UPDATE world_table Set title = :title, summary = :summary WHERE id = :id")
    suspend fun update(id: Int?, title: String?, summary: String?)
}