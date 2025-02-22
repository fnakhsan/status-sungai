package com.statussungai.android.data.network.model


import com.google.gson.annotations.SerializedName

data class AddRiverResponse(
    @SerializedName("status")
    val status: String, // success
    @SerializedName("message")
    val message: String, // River berhasil ditambahkan
    @SerializedName("data")
    val `data`: Data
) {
    data class Data(
        @SerializedName("id")
        val id: Int, // 6
        @SerializedName("name")
        val name: String, // Sungai Kapuas
        @SerializedName("created_at")
        val createdAt: String, // 2025-02-22 01:54:26
        @SerializedName("updated_at")
        val updatedAt: String // 2025-02-22 01:54:26
    )
}