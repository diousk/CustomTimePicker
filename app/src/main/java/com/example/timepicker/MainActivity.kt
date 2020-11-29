package com.example.timepicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.timepicker.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.picker.setOnClickListener {
            NumberPickerFragment().show(supportFragmentManager, "picker")
        }

    }
}