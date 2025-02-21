package com.statussungai.android.ui

import android.util.TypedValue
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


object ActivityHelper {
    fun setupActivity(
        activity: ComponentActivity,
        root: View,
        leftPad: Int = 0,
        topPad: Int = 0,
        rightPad: Int = 0,
        bottomPad: Int = 0
    ) {
        activity.enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
            v.setPadding(
                systemBars.left + pxToDp(activity = activity, value = leftPad),
                systemBars.top + pxToDp(activity = activity, value = topPad),
                systemBars.right + pxToDp(activity = activity, value = rightPad),
                systemBars.bottom + pxToDp(activity = activity, value = bottomPad)
            )
            insets
        }
        activity.setContentView(root)
    }

    fun pxToDp(activity: ComponentActivity, value: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            activity.resources.displayMetrics
        ).toInt()
    }
}