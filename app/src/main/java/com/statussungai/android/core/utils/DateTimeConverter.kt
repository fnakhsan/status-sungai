package com.statussungai.android.core.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateTimeConverter {
    fun convertDateTime(dateTimeString: String): String {
        val instant = Instant.parse(dateTimeString)
        val localDateTime = instant.atZone(ZoneId.systemDefault())

        val formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm")
        return localDateTime.format(formatter)
    }
}