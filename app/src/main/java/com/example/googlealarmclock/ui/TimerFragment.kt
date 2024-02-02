package com.example.googlealarmclock.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.googlealarmclock.R
import com.example.googlealarmclock.databinding.FragmentTimerBinding

class TimerFragment: Fragment(R.layout.fragment_timer) {
    private lateinit var binding: FragmentTimerBinding
    private val userNumbersList = mutableListOf<Int>()
    private val textViewsList = mutableListOf<TextView>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTimerBinding.bind(view)

        val window = requireActivity().window
        window.statusBarColor = ContextCompat.getColor(
            requireContext(),
            R.color.background_color
        )

        addTextViewToList()

        textViewsList.forEach { tv ->
            tv.setOnClickListener {
                addNumberToTimer(tv)
            }
        }

        binding.tvRemove.setOnClickListener {
            removeNumber()
        }

        binding.tvRemove.setOnLongClickListener {
            removeAll()
            true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setNumbers() {

        binding.apply {
            when (userNumbersList.size) {
                1 -> {
                    tvSecond.text = "0${userNumbersList[0]}"
                    tvSecond.setTextColorBlue()
                    tvSecondLetter.setTextColorBlue()
                }
                2 -> {
                    tvSecond.text = "${userNumbersList[0]}${userNumbersList[1]}"
                }
                3 -> {
                    tvMinute.text = "0${userNumbersList[0]}"
                    tvSecond.text = "${userNumbersList[1]}${userNumbersList[2]}"
                    tvMinute.setTextColorBlue()
                    tvMinuteLetter.setTextColorBlue()
                }
                4 -> {
                    tvMinute.text = "${userNumbersList[0]}${userNumbersList[1]}"
                    tvSecond.text = "${userNumbersList[2]}${userNumbersList[3]}"
                }
                5 -> {
                    tvHour.text = "0${userNumbersList[0]}"
                    tvMinute.text = "${userNumbersList[1]}${userNumbersList[2]}"
                    tvSecond.text = "${userNumbersList[3]}${userNumbersList[4]}"
                    tvHour.setTextColorBlue()
                    tvHourLetter.setTextColorBlue()
                }
                6 -> {
                    tvHour.text = "${userNumbersList[0]}${userNumbersList[1]}"
                    tvMinute.text = "${userNumbersList[2]}${userNumbersList[3]}"
                    tvSecond.text = "${userNumbersList[4]}${userNumbersList[5]}"
                }
            }
        }
    }

    private fun addTextViewToList(){
        binding.apply {
            textViewsList.add(tvOne)
            textViewsList.add(tvTwo)
            textViewsList.add(tvThree)
            textViewsList.add(tvFour)
            textViewsList.add(tvFive)
            textViewsList.add(tvSix)
            textViewsList.add(tvSeven)
            textViewsList.add(tvEight)
            textViewsList.add(tvNine)
            textViewsList.add(tvTwoZeros)
            textViewsList.add(tvZero)
        }
    }

    private fun addNumberToTimer(textView: TextView){
        val number = textView.text.toString().toInt()
        if (userNumbersList.size == 0) {
            if (textView.text.toString() != "0" && textView.text.toString() != "00") {
                userNumbersList.add(number)
            }
        } else {
            if (userNumbersList.size != 6) {
                if (textView.text.toString() == "00") {
                    if (userNumbersList.size != 5) {
                        userNumbersList.add(0)
                        userNumbersList.add(0)
                    } else {
                        userNumbersList.add(0)
                    }
                } else {
                    userNumbersList.add(number)
                }
            }
        }
        Log.d("TTTT", "$userNumbersList")
        setNumbers()
    }

    private fun removeNumber(){
        if (userNumbersList.isNotEmpty()) {
            val lastIndex = userNumbersList.lastIndex
            userNumbersList.removeAt(lastIndex)
            binding.apply {
                when (userNumbersList.size) {
                    0 -> {
                        tvSecond.text = "00"
                        tvSecond.setTextColorGrey()
                        tvSecondLetter.setTextColorGrey()
                    }
                    2 -> {
                        tvMinute.text = "00"
                        tvMinute.setTextColorGrey()
                        tvMinuteLetter.setTextColorGrey()
                    }
                    4 -> {
                        tvHour.text = "00"
                        tvHour.setTextColorGrey()
                        tvHourLetter.setTextColorGrey()
                    }
                }
            }
            setNumbers()
        }
    }

    private fun removeAll() {
        userNumbersList.clear()
        binding.apply {
            tvSecond.text = "00"
            tvMinute.text = "00"
            tvHour.text = "00"
            tvSecond.setTextColorGrey()
            tvMinute.setTextColorGrey()
            tvHour.setTextColorGrey()
            tvSecondLetter.setTextColorGrey()
            tvMinuteLetter.setTextColorGrey()
            tvHourLetter.setTextColorGrey()
        }
    }

    private fun TextView.setTextColorBlue() {
        this.setTextColor(ContextCompat.getColor(requireContext(), R.color.time_picker_hand_color))
    }

    private fun TextView.setTextColorGrey() {
        this.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey_text_color))
    }
}