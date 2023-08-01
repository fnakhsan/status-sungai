package uin.suka.status.sungai.data.network.model

import com.google.gson.annotations.SerializedName

data class LoginModel(
    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("password")
    val password: String? = null
)

data class LoginResponse(
    @field:SerializedName("data")
    val data: AccessToken? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class AccessToken(
    @field:SerializedName("accessToken")
    val accessToken: String? = null
)