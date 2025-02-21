package com.statussungai.android.data.network.model

import com.google.gson.annotations.SerializedName

data class AddPointModel(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("active")
	val active: String,

	@field:SerializedName("latitude")
	val latitude: Double,

	@field:SerializedName("longitude")
	val longitude: Double
)

data class AddPointResponse(

	@field:SerializedName("data")
	val data: AddPointData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class AddPointData(

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("latitude")
	val latitude: Any,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("active")
	val active: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("segment_id")
	val segmentId: String,

	@field:SerializedName("longitude")
	val longitude: Any
)

