package com.statussungai.android.data.network.model

import com.google.gson.annotations.SerializedName

data class RegisterModel(

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("riverId")
    val riverId: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val community: String? = null,

    @field:SerializedName("active")
    val active: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("username")
    val username: String? = null
)

data class RegisterResponse(

    @field:SerializedName("data")
    val data: UserId? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class UserId(

    @field:SerializedName("userId")
    val userId: Int? = null
)
