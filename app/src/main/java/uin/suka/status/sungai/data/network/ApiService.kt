package uin.suka.status.sungai.data.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import uin.suka.status.sungai.data.network.model.LoginModel
import uin.suka.status.sungai.data.network.model.LoginResponse
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
}