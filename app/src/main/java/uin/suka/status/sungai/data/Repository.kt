package uin.suka.status.sungai.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uin.suka.status.sungai.R
import uin.suka.status.sungai.core.utils.UiText
import uin.suka.status.sungai.data.network.ApiService
import uin.suka.status.sungai.data.network.model.LoginModel
import uin.suka.status.sungai.data.network.model.LoginResponse
import uin.suka.status.sungai.data.network.model.RegisterModel
import uin.suka.status.sungai.data.network.model.RegisterResponse

class Repository(
    private val apiService: ApiService
) {
    fun registerUser(
        username: String,
        password: String,
        name: String,
        community: String?
    ): Flow<Resource<RegisterResponse>> =
        flow {
            emit(Resource.Loading)
            try {
                val response = apiService.register(
                    RegisterModel(
                        username = username,
                        password = password,
                        name = name,
                        description = community,
                        type = "relawan",
                        active = "active",
                    )
                )
                emit(Resource.Success(response))
            } catch (e: Exception) {
                if (e.message.isNullOrBlank()) {
                    emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
                } else {
                    emit(Resource.Error(UiText.DynamicString(e.message.toString())))
                }
            }
        }.flowOn(Dispatchers.IO)

    fun loginUser(username: String, password: String): Flow<Resource<LoginResponse>> = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.login(
                LoginModel(
                    username = username,
                    password = password
                )
            )
            emit(Resource.Success(response))
        } catch (e: Exception) {
            if (e.message.isNullOrBlank()) {
                emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
            } else {
                emit(Resource.Error(UiText.DynamicString(e.message.toString())))
            }
        }
    }.flowOn(Dispatchers.IO)

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService
        ): Repository = instance ?: synchronized(this) {
            instance ?: Repository(apiService)
        }.also { instance = it }
    }
}