package com.example.simplenotepad.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.simplenotepad.Database.WorldDatabase
import com.example.simplenotepad.Database.WorldRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorldViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : WorldRepository

    val allworlds : LiveData<List<WorldData>>

    init {
        val dao = WorldDatabase.getDatabase(application).getWorldDao()
        repository = WorldRepository(dao)
        allworlds = repository.allWorlds
    }

    fun deleteWorld(wdata : WorldData) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(wdata)
    }

    fun insertWorld(wdata : WorldData) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(wdata)
    }

    fun updateNote(wdata : WorldData) = viewModelScope.launch(Dispatchers.IO){
        repository.update(wdata)
    }
}