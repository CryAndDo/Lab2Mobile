package com.example.lab2

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.lab2.database.HockeyDatabase
import com.example.lab2.model.Hockey
import java.util.UUID
import java.util.concurrent.Executors

private const val DATABASE_NAME="hockey-database"
class HockeyRepository private constructor(context:Context)
{
    private val database:HockeyDatabase=
        Room.databaseBuilder(
            context.applicationContext,
            HockeyDatabase::class.java,
            DATABASE_NAME
        ).build()
    private val hockeyDao=database.hockeyDao()
    private val executor=Executors.newSingleThreadExecutor()
    fun getHockeys():LiveData<List<Hockey>> = hockeyDao.getHockeys()
    fun getHockey(id:UUID):LiveData<Hockey?> = hockeyDao.getHockey(id)
    fun insertHockey(hockey:Hockey){
        executor.execute {
            hockeyDao.insertHockey(hockey)
        }
    }
    fun updateHockey(hockey: Hockey){
        executor.execute{
            hockeyDao.updateHockey(hockey)
        }
    }
    companion object {
        private var INSTANCE: HockeyRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = HockeyRepository(context)
            }
        }
        fun get(): HockeyRepository {
            return INSTANCE ?: throw IllegalStateException("HockeyRepository must be init")
        }
    }
}