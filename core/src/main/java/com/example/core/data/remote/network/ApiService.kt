package com.example.core.data.remote.network

import com.example.core.data.remote.response.AddBiotilikBody
import com.example.core.data.remote.response.AddBiotilikResponse
import com.example.core.data.remote.response.AddPointBody
import com.example.core.data.remote.response.AddPointResponse
import com.example.core.data.remote.response.GetPointByIdResponse
import com.example.core.data.remote.response.GetPointModel
import com.example.core.data.remote.response.GetStatusDataResponse
import com.example.core.data.remote.response.LoginBody
import com.example.core.data.remote.response.LoginResponse
import com.example.core.data.remote.response.RegisterBody
import com.example.core.data.remote.response.RegisterResponse
import com.example.core.data.remote.response.SegmentsModel
import com.example.core.data.remote.response.ViewsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("register")
    suspend fun register(
        @Body register: RegisterBody
    ): RegisterResponse

    @POST("login")
    suspend fun login(
        @Body login: LoginBody
    ): LoginResponse

    @GET("segments/1")
    suspend fun segments(): SegmentsModel

    @GET("views")
    suspend fun views(
        @Query("riverId") riverId: Int? = null,
        @Query("seasonId") seasonId: Int? = null,
        @Query("year") year: Int? = null,
        @Query("method") method: String? = null
    ): ViewsResponse

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
        @Body pointModel: AddPointBody
    ): AddPointResponse

    @POST("datas/{pointId}")
    suspend fun addBiotilik(
        @Header("Authorization") token: String,
        @Path("pointId") pointId: String,
        @Body addBiotilikBody: AddBiotilikBody
    ): AddBiotilikResponse

    @GET("users/{userId}")
    suspend fun getUserByUserId(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): GetUserResponse
}