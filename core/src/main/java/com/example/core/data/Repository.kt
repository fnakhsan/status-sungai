package com.example.core.data

import android.util.Log
import com.example.core.R
import com.example.core.data.remote.RemoteDataSource
import com.example.core.data.remote.response.AddBiotilikModel
import com.example.core.data.remote.response.RegisterResponse
import com.example.core.domain.ICoreRepository
import com.example.core.domain.model.AddPointModel
import com.example.core.domain.model.LoginModel
import com.example.core.domain.model.PointModel
import com.example.core.domain.model.RegisterModel
import com.example.core.domain.model.SegmentModel
import com.example.core.utils.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
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
        addBiotilikModel: AddBiotilikModel
    ): Flow<Resource<UiText>> = flow {

    }

    fun views(): Flow<Resource<ViewsModel>> = channelFlow {
        send(Resource.Loading)
        try {
            getRiverId().collectLatest { riverId ->
                getSeasonId().collectLatest { seasonId ->
                    getYear().collectLatest { year ->
                        val response = apiService.views(riverId, seasonId, year)
                        send(Resource.Success(response))
                    }
                }
            }
        } catch (e: Exception) {
            if (e.message.isNullOrBlank()) {
                send(Resource.Error(UiText.StringResource(R.string.unknown_error)))
            } else {
                send(Resource.Error(UiText.DynamicString(e.message.toString())))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getAllPoints(): Flow<Resource<List<PointsItem>>> = channelFlow {
        send(Resource.Loading)
        try {
            getToken().collectLatest { token ->
                val response = apiService.getAllPoints(generateBearerToken(token.toString()))
                getRiverId().collectLatest { riverId ->
                    if (riverId != null)
                        send(Resource.Success(response.data.points.filter {
                            Log.d("sort", "id $riverId")
                            it.riverId == riverId
                        }))
                }
//                Log.d("sort", "1 ${response.data.points}")
//                getSortAlphabetically().collectLatest { isAscending ->
//                    if (isAscending != null) {
//                        if (isAscending) response.data.points.sortedBy {
//                            it.name
//                        } else response.data.points.sortedByDescending {
//                            it.name
//                        }
//                    } else response.data.points
//                }
//                Log.d("sort", "2 ${response.data.points}")
//                getSortDate().collectLatest { isAscending ->
//                    if (isAscending != null) {
//                        if (isAscending) response.data.points.sortedBy {
//                            it.createdAt
//                        } else response.data.points.sortedByDescending {
//                            it.createdAt
//                        }
//                    } else response.data.points
//                }
//                Log.d("sort", "3 ${response.data.points}")
//                send(Resource.Success(response.data.points))
            }
        } catch (e: Exception) {
            if (e.message.isNullOrBlank()) {
                send(Resource.Error(UiText.StringResource(R.string.unknown_error)))
            } else {
                send(Resource.Error(UiText.DynamicString(e.message.toString())))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getPointById(pointId: String): Flow<Resource<PointsItem>> = channelFlow {
        send(Resource.Loading)
        try {
            getToken().collectLatest {
                val response = apiService.getPointById(generateBearerToken(it.toString()), pointId)
                send(Resource.Success(response.data.first()))
            }
        } catch (e: Exception) {
            if (e.message.isNullOrBlank()) {
                send(Resource.Error(UiText.StringResource(R.string.unknown_error)))
            } else {
                send(Resource.Error(UiText.DynamicString(e.message.toString())))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getStatusByPointId(pointId: String): Flow<Resource<List<DataItem>>> = channelFlow {
        send(Resource.Loading)
        try {
            getToken().collectLatest { token ->
                val response =
                    apiService.getStatusByPointId(generateBearerToken(token.toString()), pointId)
                response.data?.filter {
                    it?.status != null
                }
                if (response.data != null) {
                    send(Resource.Success(response.data.filterNotNull()))
                } else {
                    send(Resource.Error(UiText.StringResource(R.string.empty_data)))
                }
            }
        } catch (e: Exception) {
            if (e.message.isNullOrBlank()) {
                send(Resource.Error(UiText.StringResource(R.string.unknown_error)))
            } else {
                send(Resource.Error(UiText.DynamicString(e.message.toString())))
            }
        }
    }

    fun addPoint(name: String, latitude: Double, longitude: Double) = channelFlow {
        send(Resource.Loading)
        try {
            getToken().collectLatest {
                val response =
                    apiService.addPoints(
                        generateBearerToken(it.toString()),
                        "15",
                        AddPointModel(name, "active", latitude, longitude)
                    )
                send(Resource.Success(response))
            }
        } catch (e: Exception) {
            if (e.message.isNullOrBlank()) {
                send(Resource.Error(UiText.StringResource(R.string.unknown_error)))
            } else {
                send(Resource.Error(UiText.DynamicString(e.message.toString())))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun addBiotilik(
        pointId: String,
        biotilikResult: BiotilikResult,
        seasonType: SeasonType,
        year: Int
    ) =
        channelFlow {
            send(Resource.Loading)
            try {
                getToken().collectLatest {
                    val response =
                        apiService.addBiotilik(
                            generateBearerToken(it.toString()),
                            pointId,
                            AddBiotilikModel(
                                "biotilik",
                                biotilikResult,
                                getIdBySeasonValue(seasonType),
                                year
                            )
                        )
                    send(Resource.Success(response))
                }
            } catch (e: Exception) {
                if (e.message.isNullOrBlank()) {
                    send(Resource.Error(UiText.StringResource(R.string.unknown_error)))
                } else {
                    send(Resource.Error(UiText.DynamicString(e.message.toString())))
                }
            }
        }.flowOn(Dispatchers.IO)

    override fun getUserById(userId: String): Flow<Resource<GetUserResponse>> = channelFlow {
        send(Resource.Loading)
        try {
            getToken().collectLatest { token ->
                if (token.isNullOrBlank()) {
                    send(Resource.Error(UiText.DynamicString(UserType.GUEST.type)))
                } else {
                    val response = apiService.getUserByUserId(generateBearerToken(token), userId)
                    send(Resource.Success(response))
                }
            }
        } catch (e: Exception) {
            if (e.message.isNullOrBlank()) {
                send(Resource.Error(UiText.StringResource(R.string.unknown_error)))
            } else {
                send(Resource.Error(UiText.DynamicString(e.message.toString())))
            }
        }
    }.flowOn(Dispatchers.IO)

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            authDataStore: AuthDataStore,
            filterDataStore: FilterDataStore
        ): Repository = instance ?: synchronized(this) {
            instance ?: Repository(apiService, authDataStore, filterDataStore)
        }.also { instance = it }
    }
}