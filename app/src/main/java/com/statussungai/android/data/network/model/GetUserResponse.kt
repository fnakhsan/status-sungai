package com.statussungai.android.data.network.model

import com.google.gson.annotations.SerializedName

data class GetUserResponse(

	@field:SerializedName("data")
	val data: User,

	@field:SerializedName("status")
	val status: String
)

data class User(

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("active")
	val active: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("river_id")
	val riverId: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("username")
	val username: String
)
