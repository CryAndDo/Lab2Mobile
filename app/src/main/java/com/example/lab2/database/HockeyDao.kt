package com.example.lab2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.lab2.model.Hockey
import java.util.UUID

@Dao
interface HockeyDao
{
    @Query("SELECT * FROM Hockey")
    fun getHockeys():LiveData<List<Hockey>>

    @Query("SELECT * FROM Hockey WHERE id=(:id)")
    fun getHockey(id:UUID):LiveData<Hockey?>

    @Insert
    fun insertHockey(hockey: Hockey)

    @Update
    fun updateHockey(hockey: Hockey)

    @Delete
    fun deleteHockey(hockey: Hockey)

}