package com.example.core.data

import com.example.core.R
import com.example.core.data.remote.RemoteDataSource
import com.example.core.domain.ICoreRepository
import com.example.core.domain.model.AddBiotilikModel
import com.example.core.domain.model.AddPointModel
import com.example.core.domain.model.LoginModel
import com.example.core.domain.model.PointModel
import com.example.core.domain.model.RegisterModel
import com.example.core.domain.model.SegmentModel
import com.example.core.domain.model.StatusModel
import com.example.core.domain.model.UserModel
import com.example.core.utils.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ICoreRepository {
    override fun registerUser(registerModel: RegisterModel): Flow<Resource<UiText>> = flow {
        remoteDataSource.registerUser(registerModel).collectLatest {
            when (it) {
                Resource.Loading -> emit(Resource.Loading)
                is Resource.Success -> emit(Resource.Success(UiText.StringResource(R.string.register_success)))
                is Resource.Error -> emit(Resource.Error(it.error))
            }
        }
    }

    override fun loginUser(loginModel: LoginModel): Flow<Resource<UiText>> = flow {
        remoteDataSource.loginUser(loginModel).collectLatest {
            when (it) {
                Resource.Loading -> emit(Resource.Loading)
                is Resource.Success -> {
//                    it.data.accessToken.let { saveToken(it) }
//                    it.data.type.let { saveRole(it) }
//                    it.data.id.let { saveUserId(it) }
                    emit(Resource.Success(UiText.StringResource(R.string.register_success)))
                }

                is Resource.Error -> emit(Resource.Error(it.error))
            }
        }
    }

    override fun logoutUser(): Flow<Resource<UiText>> = flow {
        emit(Resource.Loading)
//        clearToken()
//        clearRole()
//        clearUserId()
        emit(Resource.Success(UiText.StringResource(R.string.logout_success)))
    }.catch {
        if (it.message.isNullOrBlank()) {
            emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
        } else {
            emit(Resource.Error(UiText.DynamicString(it.message.toString())))
        }
    }.flowOn(Dispatchers.IO)

    override fun getUserById(userId: String): Flow<Resource<UserModel>> {
        TODO("Not yet implemented")
    }

    override fun getSegments(): Flow<Resource<List<SegmentModel>>> = flow {
        remoteDataSource.getSegments().collectLatest {
            when (it) {
                Resource.Loading -> emit(Resource.Loading)
                is Resource.Success -> emit(Resource.Success(it.data))
                is Resource.Error -> emit(Resource.Error(it.error))
            }
        }
    }

    override fun getPoints(token: String): Flow<Resource<List<PointModel>>> = flow {
        remoteDataSource.getPoints(token).collectLatest {
            when (it) {
                Resource.Loading -> emit(Resource.Loading)
                is Resource.Success -> emit(Resource.Success(it.data))
                is Resource.Error -> emit(Resource.Error(it.error))
            }
        }
    }

    override fun getPointById(token: String, pointId: String): Flow<Resource<PointModel>> = flow {
        remoteDataSource.getPointById(token, pointId).collectLatest {
            when (it) {
                Resource.Loading -> emit(Resource.Loading)
                is Resource.Success -> emit(Resource.Success(it.data))
                is Resource.Error -> emit(Resource.Error(it.error))
            }
        }
    }

    override fun getStatusByPointId(token: String, pointId: String): Flow<Resource<List<StatusModel>>> = flow {
        remoteDataSource.getStatusByPointById(token, pointId).collectLatest {
            when(it) {
                Resource.Loading -> emit(Resource.Loading)
                is Resource.Success -> emit(Resource.Success(it.data))
                is Resource.Error -> emit(Resource.Error(it.error))
            }
        }
    }

    override fun addPoint(token: String, addPointModel: AddPointModel): Flow<Resource<UiText>> =
        flow {
            remoteDataSource.addPoints(token, addPointModel).collectLatest {
                when (it) {
                    Resource.Loading -> emit(Resource.Loading)
                    is Resource.Success -> emit(Resource.Success(UiText.StringResource(R.string.register_success)))
                    is Resource.Error -> emit(Resource.Error(it.error))
                }
            }
        }

    override fun addBiotilik(
        token: String,
        pointId: String,
        addBiotilikModel: AddBiotilikModel
    ): Flow<Resource<UiText>> = flow {
        remoteDataSource.addBiotilik(token, pointId, addBiotilikModel).collectLatest {
            when (it) {
                Resource.Loading -> emit(Resource.Loading)
                is Resource.Success -> emit(Resource.Success(UiText.StringResource(R.string.register_success)))
                is Resource.Error -> emit(Resource.Error(it.error))
            }
        }
    }
}