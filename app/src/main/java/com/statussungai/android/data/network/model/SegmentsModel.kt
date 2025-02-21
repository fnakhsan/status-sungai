package com.statussungai.android.data.network.model

import com.google.gson.annotations.SerializedName

data class SegmentsModel(

	@field:SerializedName("data")
	val data: SegmentsData,

	@field:SerializedName("status")
	val status: String
)

data class SegmentsData(

	@field:SerializedName("segments")
	val segments: List<SegmentsItem>
)

//data class SegmentsItem(
//
//	@field:SerializedName("name")
//	val name: String,
//
//	@field:SerializedName("active")
//	val active: String,
//
//	@field:SerializedName("river_id")
//	val riverId: Int,
//
//	@field:SerializedName("id")
//	val id: Int,
//
//	@field:SerializedName("detail")
//	val detail: List<DetailItem>
//)


data class DetailItem(

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("latitude")
	val latitude: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("segment_id")
	val segmentId: Int,

	@field:SerializedName("longitude")
	val longitude: String
)