package com.example.core.domain.model

data class AddBiotilikModel (
    val seasonId: Int,
    val year: Int,
    val familyType: Int,
    val eptType: Int,
    val eptPercentage: Float,
    val biotilikIndex: Float
)