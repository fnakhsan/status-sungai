package com.statussungai.android.data.network.model

import com.google.gson.annotations.SerializedName

data class GetStatusDataResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItem(

	@field:SerializedName("point_id")
	val pointId: Int? = null,

	@field:SerializedName("method")
	val method: String? = null,

	@field:SerializedName("year")
	val year: Int? = null,

	@field:SerializedName("changeAccess")
	val changeAccess: Boolean? = null,

	@field:SerializedName("active")
	val active: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("season_id")
	val seasonId: Int? = null,

	@field:SerializedName("result")
	val result: BiotilikResult? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("data_id")
	val dataId: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class BiotilikResult(

	@field:SerializedName("persen_ept")
	val persenEpt: Float? = null,

	@field:SerializedName("indeks_biotilik")
	val indeksBiotilik: Float? = null,

	@field:SerializedName("jenis_famili")
	val jenisFamili: Int? = null,

	@field:SerializedName("jenis_ept")
	val jenisEpt: Int? = null
)
