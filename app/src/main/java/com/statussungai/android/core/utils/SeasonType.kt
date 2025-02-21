package com.statussungai.android.core.utils

enum class SeasonType(val value: String) {
    SEASON1("Kemarau"),
    SEASON2("Hujan");

    companion object {
        fun getSeasonTypeById(id: Int) = when (id) {
            1 -> SEASON1
            2 -> SEASON2
            else -> SEASON1
        }

        fun getSeasonValueById(id: Int) = when (id) {
            1 -> SEASON1.value
            2 -> SEASON2.value
            else -> SEASON1.value
        }

        fun getIdBySeasonValue(seasonType: SeasonType) = when(seasonType.value) {
            SEASON1.value -> 1
            SEASON2.value -> 2
            else -> 1
        }
    }
}