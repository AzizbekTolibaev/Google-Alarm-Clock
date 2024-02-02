package com.example.googlealarmclock.data.entites

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat

@Entity(tableName = "table_clock")
data class Clock(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val timeZone: String = "Asia",
    val timeCountry: String
)
