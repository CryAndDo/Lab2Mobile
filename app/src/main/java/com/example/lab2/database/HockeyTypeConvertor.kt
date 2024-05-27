package com.example.lab2.database

import androidx.room.TypeConverter
import java.util.UUID

class HockeyTypeConvertor {
    @TypeConverter
    fun toUUID(uuid:String?):UUID?{
        return UUID.fromString(uuid)
    }
    @TypeConverter
    fun fromUUID(uuid: UUID?):String?{
        return uuid?.toString()
    }
}