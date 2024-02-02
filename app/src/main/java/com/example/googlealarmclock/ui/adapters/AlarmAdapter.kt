package com.example.googlealarmclock.ui.adapters

import android.content.Context
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.googlealarmclock.R
import com.example.googlealarmclock.data.entites.Alarm
import com.example.googlealarmclock.databinding.ItemAlarmBinding
import com.example.googlealarmclock.utils.daysToTextView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AlarmAdapter : ListAdapter<Alarm, AlarmAdapter.AlarmViewHolder>(myDiffCallback) {
    private lateinit var vibratePhone: Vibrator

    private var onAlarmDeleteClickListener: ((Alarm) -> Unit)? = null
    private var onAlarmDateSettingClickListener: ((Alarm) -> Unit)? = null
    private var onAlarmTimeUpdateClickListener: ((Alarm) -> Unit)? = null
    private var onAlarmDatesClickListener: ((Alarm) -> Unit)? = null
    private var onAlarmDescriptionClickListener: ((Alarm) -> Unit)? = null
    private var onAlarmVibrateClickListener: ((Alarm) -> Unit)? = null
    private var onAlarmSwitch: ((Alarm) -> Unit)? = null

    fun setOnAlarmDeleteClickListener(block: (Alarm) -> Unit) {
        onAlarmDeleteClickListener = block
    }

    fun setOnAlarmDateSettingClickListener(block: (Alarm) -> Unit) {
        onAlarmDateSettingClickListener = block
    }

    fun setOnAlarmTimeUpdateClickListener(block: (Alarm) -> Unit) {
        onAlarmTimeUpdateClickListener = block
    }

    fun setOnAlarmDatesClickListener(block: (Alarm) -> Unit) {
        onAlarmDatesClickListener = block
    }

    fun setOnAlarmDescriptionClickListener(block: (Alarm) -> Unit) {
        onAlarmDescriptionClickListener = block
    }

    fun setOnAlarmVibrateClickListener(block: (Alarm) -> Unit) {
        onAlarmVibrateClickListener = block
    }

    fun setOnAlarmSwitch(block: (Alarm) -> Unit) {
        onAlarmSwitch = block
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        return AlarmViewHolder(
            ItemAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.bind()
    }


    // ViewHolder

    inner class AlarmViewHolder(private val binding: ItemAlarmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val alarmData = getItem(adapterPosition)
            binding.tvTime.text = alarmData.time
            binding.alarmDescription.text = alarmData.description
            binding.tvMonday.isSelected = alarmData.isMondayActivated
            binding.tvTuesday.isSelected = alarmData.isTuesdayActivated
            binding.tvWednesday.isSelected = alarmData.isWednesdayActivated
            binding.tvThursday.isSelected = alarmData.isThursdayActivated
            binding.tvFriday.isSelected = alarmData.isFridayActivated
            binding.tvSaturday.isSelected = alarmData.isSaturdayActivated
            binding.tvSunday.isSelected = alarmData.isSundayActivated
            daysToTextView(binding.tvAllDates, alarmData)
            binding.alarmVibrate.isSelected = alarmData.isCheckedVibrate

            binding.tvTime.setOnClickListener {
                onAlarmTimeUpdateClickListener?.invoke(alarmData)
            }

            binding.alarmDelete.setOnClickListener {
                onAlarmDeleteClickListener?.invoke(alarmData)
            }

            binding.alarmDateSetting.setOnClickListener {
                onAlarmDateSettingClickListener?.invoke(alarmData)
            }

            binding.apply {
                alarmRingtone.setOnClickListener { }
            }

            binding.layoutExpandedShow.setOnClickListener {
                if (binding.expandableLayout.isExpanded) {
                    binding.expandableLayout.collapse()
                    binding.expandableDescription.collapse()
                } else {
                    binding.expandableLayout.toggle()
                    binding.expandableDescription.toggle()
                }
            }

            binding.backgroundLayout.setOnClickListener {
                if (binding.expandableLayout.isExpanded) {
                    binding.expandableLayout.collapse()
                    binding.expandableDescription.collapse()
                } else {
                    binding.expandableLayout.toggle()
                    binding.expandableDescription.toggle()
                }
            }

            binding.tvMonday.setOnClickListener {
                binding.tvMonday.isSelected = alarmData.isMondayActivated.not()
                alarmData.isMondayActivated = alarmData.isMondayActivated.not()
                onAlarmDatesClickListener?.invoke(alarmData)
                daysToTextView(binding.tvAllDates, alarmData)
            }

            binding.tvTuesday.setOnClickListener {
                binding.tvTuesday.isSelected = alarmData.isTuesdayActivated.not()
                alarmData.isTuesdayActivated = alarmData.isTuesdayActivated.not()
                onAlarmDatesClickListener?.invoke(alarmData)
                daysToTextView(binding.tvAllDates, alarmData)
            }

            binding.tvWednesday.setOnClickListener {
                binding.tvWednesday.isSelected = alarmData.isWednesdayActivated.not()
                alarmData.isWednesdayActivated = alarmData.isWednesdayActivated.not()
                onAlarmDatesClickListener?.invoke(alarmData)
                daysToTextView(binding.tvAllDates, alarmData)
            }

            binding.tvThursday.setOnClickListener {
                binding.tvThursday.isSelected = alarmData.isThursdayActivated.not()
                alarmData.isThursdayActivated = alarmData.isThursdayActivated.not()
                onAlarmDatesClickListener?.invoke(alarmData)
                daysToTextView(binding.tvAllDates, alarmData)
            }

            binding.tvFriday.setOnClickListener {
                binding.tvFriday.isSelected = alarmData.isFridayActivated.not()
                alarmData.isFridayActivated = alarmData.isFridayActivated.not()
                onAlarmDatesClickListener?.invoke(alarmData)
                daysToTextView(binding.tvAllDates, alarmData)
            }

            binding.tvSaturday.setOnClickListener {
                binding.tvSaturday.isSelected = alarmData.isSaturdayActivated.not()
                alarmData.isSaturdayActivated = alarmData.isSaturdayActivated.not()
                onAlarmDatesClickListener?.invoke(alarmData)
                daysToTextView(binding.tvAllDates, alarmData)
            }

            binding.tvSunday.setOnClickListener {
                binding.tvSunday.isSelected = alarmData.isSundayActivated.not()
                alarmData.isSundayActivated = alarmData.isSundayActivated.not()
                onAlarmDatesClickListener?.invoke(alarmData)
                daysToTextView(binding.tvAllDates, alarmData)
            }

            binding.alarmDescription.setOnClickListener {
                val builder = MaterialAlertDialogBuilder(itemView.context)

                builder.setTitle("Описание")
                val editText = EditText(itemView.context)
                builder.setView(editText)
                builder.setPositiveButton("OK") { builder1, _ ->
                    val text = editText.text.toString()
                    alarmData.description = text
                    binding.alarmDescription.text = text
                    builder1.dismiss()
                    onAlarmDescriptionClickListener?.invoke(alarmData)
                }
                builder.setNegativeButton("Cancel") { builder2, _ ->
                    builder2.dismiss()
                }
                val dialog = builder.create()

                dialog.show()
            }

            binding.alarmVibrate.setOnClickListener {
                binding.alarmVibrate.isSelected = alarmData.isCheckedVibrate.not()
                alarmData.isCheckedVibrate = alarmData.isCheckedVibrate.not()
                onAlarmVibrateClickListener?.invoke(alarmData)
                if (alarmData.isCheckedVibrate) {
                    vibratePhone =
                        itemView.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    vibratePhone.vibrate(100)
                }
            }

            binding.dateSwitch.setOnClickListener {
                binding.dateSwitch.isChecked = alarmData.isCheckedSwitch.not()
                alarmData.isCheckedSwitch = alarmData.isCheckedSwitch.not()
                onAlarmSwitch?.invoke(alarmData)
                if (alarmData.isCheckedSwitch) {
                    binding.tvTime.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.text_color
                        )
                    )
                } else {
                    binding.tvTime.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.grey_text_color
                        )
                    )
                }
            }
        }
    }

    private object myDiffCallback : DiffUtil.ItemCallback<Alarm>() {
        override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem.id == newItem.id && oldItem.time == newItem.time && oldItem.description == newItem.description &&
                    oldItem.isMondayActivated == newItem.isMondayActivated && oldItem.isTuesdayActivated == newItem.isTuesdayActivated &&
                    oldItem.isWednesdayActivated == newItem.isWednesdayActivated && oldItem.isThursdayActivated == newItem.isThursdayActivated &&
                    oldItem.isFridayActivated == newItem.isFridayActivated && oldItem.isSaturdayActivated == newItem.isSaturdayActivated &&
                    oldItem.isSundayActivated == newItem.isSundayActivated && oldItem.isCheckedVibrate == newItem.isCheckedVibrate
        }
    }

    fun removeItem(item: Alarm){
        val currentList =  currentList.toMutableList()
        currentList.remove(item)
        submitList(currentList)
    }
}
