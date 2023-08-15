package uin.suka.status.sungai.data.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import uin.suka.status.sungai.data.network.model.AddPointModel
import uin.suka.status.sungai.data.network.model.AddPointResponse
import uin.suka.status.sungai.data.network.model.GetPointModel
import uin.suka.status.sungai.data.network.model.GetStatusDataResponse
import uin.suka.status.sungai.data.network.model.LoginModel
import uin.suka.status.sungai.data.network.model.LoginResponse
import uin.suka.status.sungai.data.network.model.PointsItem
import uin.suka.status.sungai.data.network.model.RegisterModel
import uin.suka.status.sungai.data.network.model.RegisterResponse
import uin.suka.status.sungai.data.network.model.SegmentsModel
import uin.suka.status.sungai.data.network.model.ViewsModel

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
    ): PointsItem

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
}