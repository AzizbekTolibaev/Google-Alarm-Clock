package com.example.googlealarmclock.utils

import android.widget.TextView
import com.example.googlealarmclock.data.entites.Alarm

fun daysToTextView(textView: TextView, alarmData: Alarm): String {
    val isActivatedDaysList = booleanArrayOf(
        alarmData.isMondayActivated,
        alarmData.isTuesdayActivated,
        alarmData.isWednesdayActivated,
        alarmData.isThursdayActivated,
        alarmData.isFridayActivated,
        alarmData.isSaturdayActivated,
        alarmData.isSundayActivated
    )
    val weekDays = listOf(
        "Понедельник",
        "Вторник",
        "Среда",
        "Четверг",
        "Пятница",
        "Суббота",
        "Воскресенье"
    )
    val shortWeekDays = listOf("пн", "вт", "ср", "чт", "пт", "сб", "вс")
    var oneDay = ""
    val selectedDays = mutableListOf<String>()
    for (i in isActivatedDaysList.indices) {
        if (isActivatedDaysList[i]) {
            selectedDays.add(shortWeekDays[i])
            oneDay = weekDays[i]
        }
    }
    var daySubstring = ""
    if (selectedDays.size == 7) {
        textView.text = "Каждый день"
    } else if (selectedDays.size == 1) {
        textView.text = oneDay
    } else if (selectedDays.isEmpty()) {
        textView.text = "Не установлен"
    } else {
        val lastIndex = selectedDays.toString().length - 1
        daySubstring = selectedDays.toString().substring(1, 2)
            .uppercase() + selectedDays.toString().substring(2, lastIndex)
        textView.text = daySubstring
    }
    return daySubstring
}

