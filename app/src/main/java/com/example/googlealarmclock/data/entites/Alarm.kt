package com.example.googlealarmclock.data.entites

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.material.timepicker.MaterialTimePicker

@Entity(tableName = "table_alarm")
data class Alarm(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var time: String = "12:00",
    var description: String = "Добавить ярлык",
    var isMondayActivated: Boolean = false,
    var isTuesdayActivated: Boolean = false,
    var isWednesdayActivated: Boolean = false,
    var isThursdayActivated: Boolean = false,
    var isFridayActivated: Boolean = false,
    var isSaturdayActivated: Boolean = false,
    var isSundayActivated: Boolean = false,
    var isCheckedVibrate: Boolean = false,
    var isCheckedSwitch: Boolean = false
)

