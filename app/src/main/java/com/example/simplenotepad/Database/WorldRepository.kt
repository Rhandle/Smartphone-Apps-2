package com.example.simplenotepad.Database

import androidx.lifecycle.LiveData
import com.example.simplenotepad.Models.WorldData

class WorldRepository(private val worldDao: WorldDao) {
    val allWorlds : LiveData<List<WorldData>> = worldDao.getWorldData()

    suspend fun insert(wdata : WorldData){
        worldDao.insert(wdata)
    }

    suspend fun delete(wdata : WorldData){
        worldDao.delete(wdata)
    }

    suspend fun update(wdata: WorldData){
        worldDao.update(wdata.id, wdata.title, wdata.summary)
    }
}