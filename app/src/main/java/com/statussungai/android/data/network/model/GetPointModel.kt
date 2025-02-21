package com.statussungai.android.data.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GetPointModel(

    @field:SerializedName("data")
    val data: PointsData,

    @field:SerializedName("status")
    val status: String
)

data class PointsData(

    @field:SerializedName("points")
    val points: List<PointsItem>
)


data class GetPointByIdResponse(

    @field:SerializedName("data")
    val data: List<PointsItem>,

    @field:SerializedName("status")
    val status: String
)

@Parcelize
data class PointsItem(

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("user_id")
    val userId: Int,

    @field:SerializedName("user_name")
    val userName: String,

    @field:SerializedName("changeAccess")
    val changeAccess: Boolean,

    @field:SerializedName("latitude")
    val latitude: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("active")
    val active: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("segment_id")
    val segmentId: Int,

    @field:SerializedName("longitude")
    val longitude: String,

    @field:SerializedName("river_id")
    val riverId: Int
) : Parcelable
