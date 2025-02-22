package com.statussungai.android.data.network.model


import com.google.gson.annotations.SerializedName

data class AddDetailSegmentModel(
    @SerializedName("datas")
    val datas: List<Data>
) {
    data class Data(
        @SerializedName("latitude")
        val latitude: Double, // -7.7240656222641455
        @SerializedName("longitude")
        val longitude: Double, // 110.3893486301115
        @SerializedName("code")
        val code: String // code95531674117102021041540
    )
}