package com.statussungai.android.data.network.model

import com.google.gson.annotations.SerializedName

data class AddSegmentModel(
    @field:SerializedName("active")
    val active: String,
    @field:SerializedName("name")
    val name: String
)