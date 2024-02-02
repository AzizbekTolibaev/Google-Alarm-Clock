package com.example.googlealarmclock.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.googlealarmclock.data.entites.Clock
import com.example.googlealarmclock.databinding.ItemClockBinding

class ClockAdapter : ListAdapter<Clock, ClockAdapter.ClockViewHolder>(myDiffCallback) {

    inner class ClockViewHolder(private val binding: ItemClockBinding) : ViewHolder(binding.root) {
        fun bind() {
            val clockData = getItem(adapterPosition)
            binding.clockTime.timeZone = clockData.timeZone
            binding.tvCountry.text = clockData.timeCountry
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClockViewHolder {
        return ClockViewHolder(
            ItemClockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ClockViewHolder, position: Int) {
        holder.bind()
    }

    private object myDiffCallback: DiffUtil.ItemCallback<Clock>() {
        override fun areItemsTheSame(oldItem: Clock, newItem: Clock): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Clock, newItem: Clock): Boolean {
            return oldItem.id == newItem.id && oldItem.timeCountry == newItem.timeCountry &&
                    oldItem.timeZone == newItem.timeZone
        }
    }

    fun removeItem(item: Clock){
        val currentList =  currentList.toMutableList()
        currentList.remove(item)
        submitList(currentList)
    }
}