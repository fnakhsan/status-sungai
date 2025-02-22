package com.statussungai.android.data.network.model


import com.google.gson.annotations.SerializedName

data class AddDetailSegmentResponse(
    @SerializedName("status")
    val status: String, // success
    @SerializedName("message")
    val message: String, // Detail Segment berhasil ditambahkan
    @SerializedName("data")
    val `data`: List<Data>
) {
    data class Data(
        @SerializedName("id")
        val id: Int, // 394
        @SerializedName("latitude")
        val latitude: Double, // -7.7240656222641455
        @SerializedName("longitude")
        val longitude: Double, // 110.3893486301115
        @SerializedName("segment_id")
        val segmentId: String, // 6
        @SerializedName("user_id")
        val userId: Int, // 22
        @SerializedName("created_at")
        val createdAt: String, // 2025-02-22 03:00:31
        @SerializedName("updated_at")
        val updatedAt: String, // 2025-02-22 03:00:31
        @SerializedName("code")
        val code: String // code95531674117102021041540
    )
}