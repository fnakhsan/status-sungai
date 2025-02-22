package com.statussungai.android.data.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import com.statussungai.android.data.network.model.AddBiotilikModel
import com.statussungai.android.data.network.model.AddBiotilikResponse
import com.statussungai.android.data.network.model.AddDetailSegmentModel
import com.statussungai.android.data.network.model.AddDetailSegmentResponse
import com.statussungai.android.data.network.model.AddPointModel
import com.statussungai.android.data.network.model.AddPointResponse
import com.statussungai.android.data.network.model.AddRiverModel
import com.statussungai.android.data.network.model.AddRiverResponse
import com.statussungai.android.data.network.model.AddSegmentModel
import com.statussungai.android.data.network.model.AddSegmentResponse
import com.statussungai.android.data.network.model.GetPointByIdResponse
import com.statussungai.android.data.network.model.GetPointModel
import com.statussungai.android.data.network.model.GetStatusDataResponse
import com.statussungai.android.data.network.model.GetUserResponse
import com.statussungai.android.data.network.model.LoginModel
import com.statussungai.android.data.network.model.LoginResponse
import com.statussungai.android.data.network.model.RegisterModel
import com.statussungai.android.data.network.model.RegisterResponse
import com.statussungai.android.data.network.model.SegmentsModel
import com.statussungai.android.data.network.model.ViewsModel

interface ApiService {
    @POST("register")
    suspend fun register(
        @Body register: RegisterModel
    ): RegisterResponse

    @POST("login")
    suspend fun login(
        @Body login: LoginModel
    ): LoginResponse

    @GET("segments/1")
    suspend fun segments(): SegmentsModel

    @GET("views")
    suspend fun views(
        @Query("riverId") riverId: Int? = null,
        @Query("seasonId") seasonId: Int? = null,
        @Query("year") year: Int? = null,
        @Query("method") method: String? = null
    ): ViewsModel

    @GET("points")
    suspend fun getAllPoints(
        @Header("Authorization") token: String,
    ): GetPointModel

    @GET("points/{pointId}")
    suspend fun getPointById(
        @Header("Authorization") token: String,
        @Path("pointId") pointId: String
    ): GetPointByIdResponse

    @GET("datas/{pointId}")
    suspend fun getStatusByPointId(
        @Header("Authorization") token: String,
        @Path("pointId") pointId: String
    ): GetStatusDataResponse

    @POST("points/{segmentId}")
    suspend fun addPoints(
        @Header("Authorization") token: String,
        @Path("segmentId") segmentId: String,
        @Body pointModel: AddPointModel
    ): AddPointResponse

    @POST("datas/{pointId}")
    suspend fun addBiotilik(
        @Header("Authorization") token: String,
        @Path("pointId") pointId: String,
        @Body addBiotilikModel: AddBiotilikModel
    ): AddBiotilikResponse

    @GET("users/{userId}")
    suspend fun getUserByUserId(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): GetUserResponse

    @POST("rivers")
    suspend fun addRiver(
        @Header("Authorization") token: String,
        @Body addRiverModel: AddRiverModel
    ): AddRiverResponse

    @POST("segments/{riverId}")
    suspend fun addSegment(
        @Header("Authorization") token: String,
        @Path("riverId") riverId: String,
        @Body addSegmentModel: AddSegmentModel
    ): AddSegmentResponse

    @POST("segments/detail/{segmentId}")
    suspend fun addDetailSegment(
        @Header("Authorization") token: String,
        @Path("segmentId") segmentId: String,
        @Body addDetailSegmentModel: AddDetailSegmentModel
    ): AddDetailSegmentResponse
}