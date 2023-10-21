package com.example.core.data.remote

import com.example.core.R
import com.example.core.data.Resource
import com.example.core.data.remote.network.ApiService
import com.example.core.data.remote.response.AddBiotilikResponse
import com.example.core.data.remote.response.AddPointResponse
import com.example.core.data.remote.response.LoginResponse
import com.example.core.data.remote.response.RegisterResponse
import com.example.core.domain.model.AddBiotilikModel
import com.example.core.domain.model.AddPointModel
import com.example.core.domain.model.LoginModel
import com.example.core.domain.model.PointModel
import com.example.core.domain.model.RegisterModel
import com.example.core.domain.model.SegmentModel
import com.example.core.domain.model.StatusModel
import com.example.core.utils.DataMappers.toBody
import com.example.core.utils.DataMappers.toModel
import com.example.core.utils.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    fun registerUser(
        registerModel: RegisterModel
    ): Flow<Resource<RegisterResponse>> = flow {
        emit(Resource.Loading)
        val response = apiService.register(
            registerModel.toBody()
        )
        emit(Resource.Success(response))
    }.catch {
        if (it.message.isNullOrBlank()) {
            emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
        } else {
            emit(Resource.Error(UiText.DynamicString(it.message.toString())))
        }
    }.flowOn(Dispatchers.IO)

    fun loginUser(
        loginModel: LoginModel
    ): Flow<Resource<LoginResponse>> = flow {
        emit(Resource.Loading)
        val response = apiService.login(
            loginModel.toBody()
        )
        emit(Resource.Success(response))
    }.catch {
        if (it.message.isNullOrBlank()) {
            emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
        } else {
            emit(Resource.Error(UiText.DynamicString(it.message.toString())))
        }
    }.flowOn(Dispatchers.IO)

    fun getSegments(): Flow<Resource<List<SegmentModel>>> = flow {
        emit(Resource.Loading)
        val response = apiService.views().data.rivers.toModel()
        emit(Resource.Success(response))
    }.catch {
        if (it.message.isNullOrBlank()) {
            emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
        } else {
            emit(Resource.Error(UiText.DynamicString(it.message.toString())))
        }
    }.flowOn(Dispatchers.IO)

    fun getPoints(token: String): Flow<Resource<List<PointModel>>> = flow {
        emit(Resource.Loading)
        val response = apiService.getAllPoints(generateBearerToken(token)).data.points.toModel()
        emit(Resource.Success(response))
    }.catch {
        if (it.message.isNullOrBlank()) {
            emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
        } else {
            emit(Resource.Error(UiText.DynamicString(it.message.toString())))
        }
    }.flowOn(Dispatchers.IO)

    fun getPointById(token: String, id: String): Flow<Resource<PointModel>> = flow {
        emit(Resource.Loading)
        val response = apiService.getPointById(generateBearerToken(token), id).data.toModel().first()
        emit(Resource.Success(response))
    }.catch {
        if (it.message.isNullOrBlank()) {
            emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
        } else {
            emit(Resource.Error(UiText.DynamicString(it.message.toString())))
        }
    }.flowOn(Dispatchers.IO)

    fun getStatusByPointById(token: String, id: String): Flow<Resource<List<StatusModel>>> = flow {
        emit(Resource.Loading)
        val response =
            apiService.getStatusByPointId(generateBearerToken(token), id).data.toModel()
        emit(Resource.Success(response))
    }.catch {
        if (it.message.isNullOrBlank()) {
            emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
        } else {
            emit(Resource.Error(UiText.DynamicString(it.message.toString())))
        }
    }.flowOn(Dispatchers.IO)

    fun addPoints(token: String, addPointModel: AddPointModel): Flow<Resource<AddPointResponse>> = flow {
        emit(Resource.Loading)
        val response = apiService.addPoints(generateBearerToken(token), "15", addPointModel.toBody())
        emit(Resource.Success(response))
    }.catch {
        if (it.message.isNullOrBlank()) {
            emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
        } else {
            emit(Resource.Error(UiText.DynamicString(it.message.toString())))
        }
    }.flowOn(Dispatchers.IO)

    fun addBiotilik(token: String, pointId: String, addBiotilikModel: AddBiotilikModel): Flow<Resource<AddBiotilikResponse>> = flow {
        emit(Resource.Loading)
        val response = apiService.addBiotilik(generateBearerToken(token), pointId, addBiotilikModel.toBody())
        emit(Resource.Success(response))
    }.catch {
        if (it.message.isNullOrBlank()) {
            emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
        } else {
            emit(Resource.Error(UiText.DynamicString(it.message.toString())))
        }
    }.flowOn(Dispatchers.IO)

    private fun generateBearerToken(token: String): String {
        return if (token.contains("bearer", true)) {
            token
        } else {
            "Bearer $token"
        }
    }
}