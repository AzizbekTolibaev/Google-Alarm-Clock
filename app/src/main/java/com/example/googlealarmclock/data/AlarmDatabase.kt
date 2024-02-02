package com.example.googlealarmclock.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.googlealarmclock.data.entites.Alarm
import com.example.googlealarmclock.data.dao.AlarmDao
import com.example.googlealarmclock.data.entites.Clock
import com.example.googlealarmclock.data.dao.ClockDao

@Database(entities = [Alarm::class, Clock::class], version = 6)
abstract class AlarmDatabase: RoomDatabase() {

    abstract fun getAlarmDao(): AlarmDao
    abstract fun getClockDao(): ClockDao

    companion object {
        const val DATABASE_NAME = "db_name"

        fun getInstance(context: Context): AlarmDatabase {
            return Room.databaseBuilder(
                context, AlarmDatabase::class.java, DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        }
    }
}