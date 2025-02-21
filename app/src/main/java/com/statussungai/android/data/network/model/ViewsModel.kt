package com.statussungai.android.data.network.model

import com.google.gson.annotations.SerializedName

data class ViewsModel(

    @field:SerializedName("data")
    val data: ViewsData,

    @field:SerializedName("status")
    val status: String
)

data class ViewsData(

    @field:SerializedName("rivers")
    val rivers: List<RiversItem>
)

data class RiversItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("segments")
    val segments: List<SegmentsItem>
)

data class SegmentsItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("active")
    val active: String,

    @field:SerializedName("details")
    val details: List<DetailsItem>,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("points")
    val points: List<ViewPointsItem>
)

data class DetailsItem(

    @field:SerializedName("latitude")
    val latitude: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("longitude")
    val longitude: String
)

data class ViewPointsItem(

    @field:SerializedName("datas")
    val datas: List<DatasItem>,

    @field:SerializedName("latitude")
    val latitude: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("active")
    val active: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("longitude")
    val longitude: String
)

data class DatasItem(

    @field:SerializedName("result")
    val result: Result?,

    @field:SerializedName("method")
    val method: String,

    @field:SerializedName("year")
    val year: Int,

    @field:SerializedName("active")
    val active: String,

    @field:SerializedName("season_id")
    val seasonId: Int,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("status")
    val status: Int?
)

data class Result(

    @field:SerializedName("persen_ept")
    val persenEpt: String,

    @field:SerializedName("indeks_biotilik")
    val indeksBiotilik: String,

    @field:SerializedName("jenis_famili")
    val jenisFamili: String,

    @field:SerializedName("jenis_ept")
    val jenisEpt: String
)
