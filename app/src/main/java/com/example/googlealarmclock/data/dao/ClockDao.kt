package com.example.googlealarmclock.data.dao

import androidx.room.*
import com.example.googlealarmclock.data.entites.Clock

@Dao
interface ClockDao {

    @Query("SELECT * FROM table_clock")
    suspend fun getClockList(): MutableList<Clock>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addClock(clock: Clock)

    @Delete
    suspend fun deleteClock(clock: Clock)
}