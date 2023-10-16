package com.example.core.data.remote.network

import com.example.core.data.remote.response.AddBiotilikModel
import com.example.core.data.remote.response.AddBiotilikResponse
import com.example.core.data.remote.response.AddPointModel
import com.example.core.data.remote.response.AddPointResponse
import com.example.core.data.remote.response.GetPointByIdResponse
import com.example.core.data.remote.response.GetPointModel
import com.example.core.data.remote.response.GetStatusDataResponse
import com.example.core.data.remote.response.GetUserResponse
import com.example.core.data.remote.response.LoginModel
import com.example.core.data.remote.response.LoginResponse
import com.example.core.data.remote.response.RegisterModel
import com.example.core.data.remote.response.RegisterResponse
import com.example.core.data.remote.response.SegmentsModel
import com.example.core.data.remote.response.ViewsModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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
}