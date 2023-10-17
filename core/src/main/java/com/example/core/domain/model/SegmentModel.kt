package com.example.core.domain.model

import com.example.core.data.remote.response.DetailsItem
import com.example.core.data.remote.response.ViewPointsItem
import com.google.gson.annotations.SerializedName

data class SegmentModel(
    val id: Int,
    val name: String,
    val active: String,
    val points: List<ViewPointModel>
)