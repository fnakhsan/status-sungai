package com.statussungai.android.core.utils

import java.math.BigDecimal
import java.math.RoundingMode

object Score {
    fun sumScore(
        familyType: Int,
        eptType: Int,
        eptPercentage: Float,
        biotilikIndex: Float
    ): String {
        val familyTypeScore = when (familyType) {
            in 7..9 -> 2.0f
            in 10..13 -> 3.0f
            in 14..Int.MAX_VALUE -> 4.0f
            else -> 1.0f
        }
        val eptTypeScore = when (eptType) {
            0 -> 1.0f
            in 1..2 -> 2.0f
            in 3..7 -> 3.0f
            in 8..Int.MAX_VALUE -> 4.0f
            else -> 0.0f
        }
        val eptPercentageScore = when (eptPercentage) {
            0.0f -> 1.0f
            in 0.0f..15.0f -> 2.0f
            in 15.0f..40.0f -> 3.0f
            in 40.0f..Float.MAX_VALUE -> 4.0f
            else -> 0.0f
        }

        val biotilikIndexScore =
            when (BigDecimal(biotilikIndex.toDouble()).setScale(1, RoundingMode.HALF_EVEN)
                .toFloat()) {
                in 1.0f..1.7f -> 1.0f
                in 1.8f..2.5f -> 2.0f
                in 2.6f..3.2f -> 3.0f
                in 3.3f..4.0f -> 4.0f
                else -> 0.0f
            }

        return ((familyTypeScore + eptTypeScore + eptPercentageScore + biotilikIndexScore) / 4.0f).toString()
    }
}