package com.example.core.domain.model

data class SegmentModel(
    val id: Int,
    val name: String,
    val active: String,
    val points: List<ViewPointModel>,
    val flowPoints: List<FlowPointModel>,
)