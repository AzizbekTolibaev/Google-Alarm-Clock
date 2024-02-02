package com.example.googlealarmclock.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.googlealarmclock.AlarmReceiver
import com.example.googlealarmclock.AlarmScreenActivity
import com.example.googlealarmclock.R
import com.example.googlealarmclock.data.AlarmDatabase
import com.example.googlealarmclock.data.dao.AlarmDao
import com.example.googlealarmclock.data.entites.Alarm
import com.example.googlealarmclock.databinding.FragmentAlarmBinding
import com.example.googlealarmclock.ui.adapters.AlarmAdapter
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.launch
import java.util.*

class AlarmFragment : Fragment(R.layout.fragment_alarm) {
    private lateinit var binding: FragmentAlarmBinding
    private val adapter = AlarmAdapter()
    private lateinit var dao: AlarmDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlarmBinding.bind(view)

        initVariables()
        initListeners()

    }

    private fun initListeners() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.recyclerView.layoutManager!! as LinearLayoutManager
                if (adapter.itemCount != 0) {
                    if (layoutManager.findFirstCompletelyVisibleItemPosition() > 0) {
                        binding.toolbar.background = ContextCompat.getDrawable(
                            requireContext(),
                            R.color.status_bar_color
                        )
                        val window = requireActivity().window
                        window.statusBarColor = ContextCompat.getColor(
                            requireContext(),
                            R.color.status_bar_color
                        )
                    } else {
                        binding.toolbar.background = ContextCompat.getDrawable(
                            requireContext(),
                            R.color.background_color
                        )
                        val window = requireActivity().window
                        window.statusBarColor = ContextCompat.getColor(
                            requireContext(),
                            R.color.background_color
                        )
                    }
                }
            }
        })

        adapter.setOnAlarmDatesClickListener {
            lifecycleScope.launch {
                dao.updateAlarm(it)
            }
        }

        adapter.setOnAlarmDescriptionClickListener {
            lifecycleScope.launch {
                dao.updateAlarm(it)
            }
        }

        adapter.setOnAlarmVibrateClickListener {
            lifecycleScope.launch {
                dao.updateAlarm(it)
            }
        }

        adapter.setOnAlarmSwitch {
            lifecycleScope.launch {
                dao.updateAlarm(it)
            }
        }

        binding.addAlarm.setOnClickListener {
            val timePicker =
                MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).setHour(12)
                    .setTheme(
                        R.style.MyCustomTimePicker
                    )
                    .setMinute(0).setInputMode(INPUT_MODE_CLOCK).setTitleText("Выберите время")
                    .build()

            timePicker.show(childFragmentManager, "tag")
            timePicker.addOnPositiveButtonClickListener {
                lifecycleScope.launch {
                    if (timePicker.minute < 10) {
                        if (timePicker.hour < 10) {
                            dao.addAlarm(
                                Alarm(
                                    0, "0${timePicker.hour}:0${timePicker.minute}"
                                )
                            )
                        } else {
                            dao.addAlarm(
                                Alarm(
                                    0, "${timePicker.hour}:0${timePicker.minute}"
                                )
                            )
                        }
                    } else {
                        if (timePicker.hour < 10) {
                            dao.addAlarm(
                                Alarm(
                                    0, "0${timePicker.hour}:${timePicker.minute}"
                                )
                            )
                        } else {
                            dao.addAlarm(
                                Alarm(
                                    0, "${timePicker.hour}:${timePicker.minute}"
                                )
                            )
                        }
                    }
                    val alarmSorted = dao.getAlarmList().sortedBy { it.time }.toMutableList()
                    adapter.submitList(alarmSorted)
                }
            }
        }

        adapter.setOnAlarmDeleteClickListener {
            lifecycleScope.launch {
                adapter.removeItem(it)
                dao.deleteAlarm(it)
            }
        }

        adapter.setOnAlarmTimeUpdateClickListener { alarm ->
            val updateTimePicker =
                MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).setHour(12)
                    .setTheme(
                        R.style.MyCustomTimePicker
                    )
                    .setMinute(0).setInputMode(INPUT_MODE_CLOCK).setTitleText("Обновление времени")
                    .build()

            updateTimePicker.show(childFragmentManager, "tag")

            updateTimePicker.addOnPositiveButtonClickListener {
                lifecycleScope.launch {
                    if (updateTimePicker.minute < 10) {
                        if (updateTimePicker.hour < 10) {
                            dao.updateAlarm(
                                Alarm(
                                    alarm.id,
                                    "0${updateTimePicker.hour}:0${updateTimePicker.minute}"
                                )
                            )
                        } else {
                            dao.updateAlarm(
                                Alarm(
                                    alarm.id, "${updateTimePicker.hour}:0${updateTimePicker.minute}"
                                )
                            )
                        }
                    } else {
                        if (updateTimePicker.hour < 10) {
                            dao.updateAlarm(
                                Alarm(
                                    alarm.id, "0${updateTimePicker.hour}:${updateTimePicker.minute}"
                                )
                            )
                        } else {
                            dao.updateAlarm(
                                Alarm(
                                    alarm.id, "${updateTimePicker.hour}:${updateTimePicker.minute}"
                                )
                            )
                        }
                    }
                    val alarmSorted = dao.getAlarmList().sortedBy { it.time }.toMutableList()
                    adapter.submitList(alarmSorted)
                    Log.d("RRRR", "$alarmSorted")
                }
            }
        }

        binding.tvAlarm.setOnClickListener {
            notificationScreen()
        }
    }

    private fun notificationScreen() {
        val intent = Intent(requireActivity(), AlarmReceiver::class.java)
        intent.putExtra("TITLE", "Alarm manager")
        intent.putExtra("MESSAGE", "Message")
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
        )

        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(System.currentTimeMillis()+8000, pendingIntent), pendingIntent
        )
    }

    private fun initVariables() {
        dao = AlarmDatabase.getInstance(requireContext()).getAlarmDao()
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            Log.d("TTTT","${dao.getAlarmList()}")
            adapter.submitList(dao.getAlarmList().sortedBy { it.time }.toMutableList())
        }
    }
}
