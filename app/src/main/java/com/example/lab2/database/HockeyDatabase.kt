package com.example.lab2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lab2.model.Hockey

@Database(entities = [Hockey::class], version = 2)
@TypeConverters(HockeyTypeConvertor::class)
abstract class HockeyDatabase:RoomDatabase() {
    abstract fun hockeyDao():HockeyDao
}