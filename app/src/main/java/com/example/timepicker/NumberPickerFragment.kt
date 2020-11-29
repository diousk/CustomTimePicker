package com.example.timepicker

import android.R.attr
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.annotation.ColorInt
import com.example.timepicker.databinding.FragmentNumberPickerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*


class NumberPickerFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentNumberPickerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNumberPickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val now = Calendar.getInstance()
        val hour = now.get(Calendar.HOUR_OF_DAY)
        val min = now.get(Calendar.MINUTE)
        Log.e(TAG, "hour $hour, min $min")

        with(binding){
            val dates = arrayOf("今天", "明天", "後天")
            datePicker.wrapSelectorWheel = false
            datePicker.displayedValues = dates
            datePicker.minValue = 0
            datePicker.maxValue = dates.size - 1
            datePicker.setDividerHeight(1)

            val hourIncremental = if (min > 30) 1 else 0
            val hours = (0..23).map { String.format("%02d", it) }.toTypedArray()
            hourPicker.displayedValues = hours
            hourPicker.minValue = 0
            hourPicker.maxValue = hours.size - 1
            hourPicker.setDividerHeight(1)
            hourPicker.value = hour + hourIncremental

            val minutes = (0..59).step(30).map { String.format("%02d", it) }.toTypedArray()
            minutePicker.displayedValues = minutes
            minutePicker.minValue = 0
            minutePicker.maxValue = minutes.size - 1
            minutePicker.setDividerHeight(1)
            minutePicker.value = if (min > 30) 0 else 1
        }
    }
}
private const val TAG = "WidgetUtil"

internal fun NumberPicker.setDividerColor(@ColorInt color: Int) {
    try {
        NumberPicker::class.java.getDeclaredField("mSelectionDivider").apply {
            isAccessible = true
        }.set(this, ColorDrawable(color))
        invalidate()
    } catch (e: Exception) {
        Log.e(TAG, "setDividerColor failed. " + e.message)
    }
}


internal fun NumberPicker.setDividerHeight(height: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        selectionDividerHeight = height
        return
    }

    try {
        NumberPicker::class.java.getDeclaredField("mSelectionDividerHeight")
            .apply { isAccessible = true }.set(this, height)
        invalidate()
    } catch (e: Exception) {
        Log.e(TAG, "setDividerHeight failed. " + e.message)
    }
}