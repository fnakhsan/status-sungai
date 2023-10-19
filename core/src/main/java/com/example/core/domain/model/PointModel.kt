package com.example.core.domain.model

data class PointModel(
    val id: Int,
    val name: String,
    val riverId: Int,
    val segmentId: Int,
    val createdBy: String,
    val latitude: String,
    val longitude: String,
)
