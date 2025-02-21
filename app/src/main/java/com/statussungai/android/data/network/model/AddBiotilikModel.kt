package com.statussungai.android.data.network.model

import com.google.gson.annotations.SerializedName

data class AddBiotilikModel(

	@field:SerializedName("method")
	val method: String,

	@field:SerializedName("data")
	val data: BiotilikResult,

	@field:SerializedName("seasonId")
	val seasonId: Int,

	@field:SerializedName("year")
	val year: Int
)

data class AddBiotilikResponse(

	@field:SerializedName("data")
	val data: AddBiotilikData,

	@field:SerializedName("status")
	val status: String
)

data class AddBiotilikData(

	@field:SerializedName("point_id")
	val pointId: String,

	@field:SerializedName("method")
	val method: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("year")
	val year: Int,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("active")
	val active: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("season_id")
	val seasonId: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("status")
	val status: Int
)
