package com.example.timepicker

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import timber.log.Timber
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
        binding.serviceTimePicker.setConfirmListener { dayOffset, hour, minute ->
            Timber.d("dayOffset $dayOffset, $hour, $minute")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { setupBottomSheet(it) }
        return dialog
    }

    private fun setupBottomSheet(dialogInterface: DialogInterface) {
        val bottomSheetDialog = dialogInterface as BottomSheetDialog
        val bottomSheet = bottomSheetDialog.findViewById<View>(
            com.google.android.material.R.id.design_bottom_sheet)
            ?: return
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
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