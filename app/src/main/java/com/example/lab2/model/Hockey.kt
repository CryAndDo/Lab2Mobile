package com.example.lab2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Hockey(@PrimaryKey val id:UUID=UUID.randomUUID(),
                  var name:String="", var number_of_games:Int=0,
                  var birthYear:Int=0, var number_of_missed_pucks:Int=0)