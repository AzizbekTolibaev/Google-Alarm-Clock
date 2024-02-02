package com.example.googlealarmclock.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.googlealarmclock.*
import com.example.googlealarmclock.data.AlarmDatabase
import com.example.googlealarmclock.data.dao.ClockDao
import com.example.googlealarmclock.data.entites.Clock
import com.example.googlealarmclock.databinding.FragmentClockBinding
import com.example.googlealarmclock.ui.adapters.ClockAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.util.*

class ClockFragment : Fragment(R.layout.fragment_clock) {
    private lateinit var binding: FragmentClockBinding
    private val adapter = ClockAdapter()
    private lateinit var dao: ClockDao

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentClockBinding.bind(view)

        initListener()
        initVariables()
        swipeDelete()

        calendar()
    }

    private fun initVariables() {
        dao = AlarmDatabase.getInstance(requireContext()).getClockDao()
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            adapter.submitList(dao.getClockList())
        }
    }

    private fun swipeDelete() {
        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ) = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val clock: Clock = adapter.currentList.toMutableList()[position]

                    lifecycleScope.launch {
                        dao.deleteClock(clock)
                    }
                    adapter.currentList.toMutableList().removeAt(position)
                    adapter.notifyItemRemoved(position)
                    lifecycleScope.launch {
                        adapter.submitList(dao.getClockList())
                    }
                }
            }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerView)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calendar() {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val monthOfYear = calendar.get(Calendar.MONTH)

        val weekDays = listOf("пн", "вт", "ср", "чт", "пт", "сб", "вс")
        val months = listOf("янв.", "фев.", "мар.", "апр.", "май.", "июн.", "июл.", "авг.", "сен.", "окт.", "ноя.", "дек.")
        val currentDayOfWeek = weekDays[dayOfWeek - 2]
        val currentMonthOfYear = months[monthOfYear]

        binding.clockDate.text = "$currentDayOfWeek, $dayOfMonth $currentMonthOfYear"
    }

    private fun initListener() {
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

        binding.imgAddClock.setOnClickListener {
            showCountriesDialog()
        }
    }

    private fun showCountriesDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_all_countries_clock)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val countriesListView: ListView = dialog.findViewById(R.id.list_view_countries)
        val cancelBtn = dialog.findViewById<TextView>(R.id.cancel_btn)
        val countryList = listOf(
            "Asia/Tashkent", "Asia/Karachi", "Asia/Kolkata", "Asia/Dhaka", "Asia/Bangkok", "Asia/Hong_Kong",
            "Asia/Shanghai", "Asia/Tokyo", "Australia/Sydney", "Australia/Adelaide", "Australia/Darwin",
            "Asia/Dubai", "Asia/Riyadh", "Europe/Moscow", "Europe/Istanbul", "Europe/London", "Europe/Paris",
            "Europe/Berlin", "Europe/Rome", "America/New_York"
        )

        val adapterListView = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, countryList)

        countriesListView.adapter = adapterListView
        countriesListView.setOnItemClickListener { _, _, position, _ ->
            lifecycleScope.launch {
                dao.addClock(Clock(0, countryList[position], countryList[position]))
                adapter.submitList(dao.getClockList())
            }

            dialog.dismiss()
        }

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}
