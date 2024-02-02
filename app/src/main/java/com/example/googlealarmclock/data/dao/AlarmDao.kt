package com.example.googlealarmclock.data.dao

import androidx.room.*
import com.example.googlealarmclock.data.entites.Alarm

@Dao
interface AlarmDao {

    @Query("SELECT * FROM table_alarm")
    suspend fun getAlarmList(): MutableList<Alarm>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAlarm(alarm: Alarm)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAlarm(alarm: Alarm)

    @Delete
    suspend fun deleteAlarm(alarm: Alarm)
}