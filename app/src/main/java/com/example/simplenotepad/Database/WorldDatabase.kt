package com.example.simplenotepad.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.simplenotepad.Models.WorldData
import com.example.simplenotepad.Utilities.DATABASE_NAME

@Database(entities = arrayOf(WorldData::class), version = 1, exportSchema = false)
abstract class WorldDatabase : RoomDatabase() {
    abstract fun getWorldDao() : WorldDao

    companion object{

        @Volatile
        private var INSTANCE : WorldDatabase? = null

        fun getDatabase(context: Context): WorldDatabase{

            return INSTANCE ?: synchronized(this){
                val instance =  Room.databaseBuilder(
                    context.applicationContext,
                    WorldDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}