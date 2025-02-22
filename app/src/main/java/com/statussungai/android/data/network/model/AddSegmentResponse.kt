package com.statussungai.android.data.network.model


import com.google.gson.annotations.SerializedName

data class AddSegmentResponse(
    @SerializedName("status")
    val status: String, // success
    @SerializedName("message")
    val message: String, // Segment berhasil ditambahkan
    @SerializedName("data")
    val `data`: Data
) {
    data class Data(
        @SerializedName("id")
        val id: Int, // 16
        @SerializedName("name")
        val name: String, // Segmen 1
        @SerializedName("river_id")
        val riverId: String, // 1
        @SerializedName("active")
        val active: String, // active
        @SerializedName("created_at")
        val createdAt: String, // 2025-02-22 02:06:25
        @SerializedName("updated_at")
        val updatedAt: String // 2025-02-22 02:06:25
    )
}