package com.example.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class BiotilikResponse(

	@field:SerializedName("data")
	val data: BiotilikResult,

	@field:SerializedName("status")
	val status: String
)
