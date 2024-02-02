package com.example.googlealarmclock.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.googlealarmclock.R

class StopWatchFragment: Fragment(R.layout.fragment_stop_watch) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val window = requireActivity().window
        window.statusBarColor = ContextCompat.getColor(
            requireContext(),
            R.color.background_color
        )
    }
}