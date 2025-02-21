package com.statussungai.android.data.network.model

import com.google.gson.annotations.SerializedName

data class LoginModel(
    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("password")
    val password: String? = null
)

data class LoginResponse(

    @field:SerializedName("data")
    val data: Data,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)

data class Data(

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

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

    @field:SerializedName("accessToken")
    val accessToken: String,

    @field:SerializedName("username")
    val username: String
)