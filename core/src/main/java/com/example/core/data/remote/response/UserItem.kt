package com.example.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("data")
	val data: UserItem,

	@field:SerializedName("status")
	val status: String
)

data class UserItem(

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
