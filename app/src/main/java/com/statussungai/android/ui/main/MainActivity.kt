package com.statussungai.android.ui.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.statussungai.android.databinding.ActivityMainBinding
import com.statussungai.android.ui.ActivityHelper.setupActivity
import com.statussungai.android.ui.home.HomeActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupActivity(activity = this, root = binding.root)
        toHome()
    }

//    private fun toLogin() {
//        val intent = Intent(this@MainActivity, LoginActivity::class.java)
//        showLoading(false)
//        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
//        finish()
//    }

    private fun toHome() {
        val intent = Intent(this@MainActivity, HomeActivity::class.java)
//        intent.putExtra(EXTRA_TOKEN, token)
//        showLoading(false)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        finish()
    }

//    private fun showLoading(isLoading: Boolean) {
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }
}