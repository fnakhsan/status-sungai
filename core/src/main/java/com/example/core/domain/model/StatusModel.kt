package com.example.core.domain.model

data class StatusModel(
    val seasonId: Int,
    val year: Int,
    val status: Int,
    val date: String,
    val familyType: Int,
    val eptType: Int,
    val eptPercentage: Float,
    val biotilikIndex: Float
)
