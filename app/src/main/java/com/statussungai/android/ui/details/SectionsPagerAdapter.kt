package com.statussungai.android.ui.details

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity, private val pointId: Int) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 1

    override fun createFragment(position: Int): Fragment {
        Log.d("pointid", "terima $pointId")
        return DetailFragment.newInstance(position + 1, pointId)
    }
}