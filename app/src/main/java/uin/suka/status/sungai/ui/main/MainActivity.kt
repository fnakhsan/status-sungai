package uin.suka.status.sungai.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uin.suka.status.sungai.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}