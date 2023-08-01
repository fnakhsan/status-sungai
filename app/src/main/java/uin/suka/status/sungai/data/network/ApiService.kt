package uin.suka.status.sungai.data.network

import retrofit2.http.Body
import retrofit2.http.POST
import uin.suka.status.sungai.data.network.model.LoginModel
import uin.suka.status.sungai.data.network.model.LoginResponse
import uin.suka.status.sungai.data.network.model.RegisterModel
import uin.suka.status.sungai.data.network.model.RegisterResponse

interface ApiService {
    @POST("register")
    suspend fun register(
        @Body register: RegisterModel
    ): RegisterResponse

    @POST("login")
    suspend fun login(
        @Body login: LoginModel
    ): LoginResponse
}