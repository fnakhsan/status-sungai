package com.statussungai.android.core.utils

import androidx.annotation.StringRes
import com.statussungai.android.R

enum class RiverStatusUtil(private val id: Int?, @StringRes private val status: Int) {
    STATUS0(null, R.string.status0),
    STATUS1(1, R.string.status1),
    STATUS2(2, R.string.status2),
    STATUS3(3, R.string.status3),
    STATUS4(4, R.string.status4);

    companion object {
        fun getStatusById(id: Int?) = when (id) {
            STATUS1.id -> STATUS1.status
            STATUS2.id -> STATUS2.status
            STATUS3.id -> STATUS3.status
            STATUS4.id -> STATUS4.status
            else -> STATUS0.status
        }
    }
}