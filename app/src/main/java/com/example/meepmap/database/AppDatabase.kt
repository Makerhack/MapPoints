package com.example.meepmap.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.meepmap.model.Resource

@Database(entities = [Resource::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): ResourcesDao

    companion object{
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "roomdb")
                    .build()
            }

            return INSTANCE as AppDatabase
        }
    }
}