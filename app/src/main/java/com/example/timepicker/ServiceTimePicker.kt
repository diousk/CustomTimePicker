package com.example.timepicker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.timepicker.databinding.ViewServiceTimePickerBinding
import java.util.*

class ServiceTimePicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding = ViewServiceTimePickerBinding
        .inflate(LayoutInflater.from(context), this, true)

    fun interface OnConfirmListener {
        fun onConfirm(dayOffset: Int, hour: Int, minute: Int)
    }
    private var confirmListener: OnConfirmListener? = null
    fun setConfirmListener(listener: OnConfirmListener) {
        confirmListener = listener
    }

    fun interface OnDismissListener {
        fun onDismiss()
    }

    init {
        binding.confirm.setOnClickListener {
            val dayOffset = binding.datePicker.value
            val hour = binding.hourPicker.value
            val minute = binding.minutePicker.value * MINUTE_INTERVAL
            confirmListener?.onConfirm(dayOffset, hour, minute)
        }

        val now = Calendar.getInstance()
        val nowHour = now.get(Calendar.HOUR_OF_DAY)
        val nowMin = now.get(Calendar.MINUTE)

        // minute picker
        val incHour = with(binding) {
            val stepForward = (nowMin >= MINUTE_INTERVAL)
            val minutes = (0..59).step(MINUTE_INTERVAL)
                .map { String.format("%02d", it) }.toTypedArray()
            minutePicker.displayedValues = minutes
            minutePicker.minValue = 0
            minutePicker.maxValue = minutes.size - 1
            minutePicker.setDividerHeight(1)
            minutePicker.value = if (stepForward) 0 else 1
            stepForward
        }

        // hour picker
        val incDay = with(binding) {
            val hourIncremental = if (incHour) 1 else 0
            val targetHour = nowHour + hourIncremental
            val hours = (0..23).map { String.format("%02d", it) }.toTypedArray()
            hourPicker.displayedValues = hours
            hourPicker.minValue = 0
            hourPicker.maxValue = hours.size - 1
            hourPicker.setDividerHeight(1)
            hourPicker.value = targetHour % 24
            targetHour >= 24
        }

        // date picker
        with(binding) {
            val dates = resources.getStringArray(R.array.readable_date_offset)
            datePicker.wrapSelectorWheel = false
            datePicker.displayedValues = dates
            datePicker.minValue = 0
            datePicker.maxValue = dates.size - 1
            datePicker.setDividerHeight(DIVIDER_HEIGHT)
            datePicker.value = if (incDay) 1 else 0
        }
    }

    companion object {
        private const val DIVIDER_HEIGHT = 1
        private const val MINUTE_INTERVAL = 30
    }
}