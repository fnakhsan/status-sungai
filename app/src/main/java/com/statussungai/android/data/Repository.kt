package com.statussungai.android.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.statussungai.android.R
import com.statussungai.android.core.utils.SeasonType
import com.statussungai.android.core.utils.SeasonType.Companion.getIdBySeasonValue
import com.statussungai.android.core.utils.UiText
import com.statussungai.android.core.utils.UserType
import com.statussungai.android.data.local.datastore.AuthDataStore
import com.statussungai.android.data.local.datastore.FilterDataStore
import com.statussungai.android.data.network.ApiService
import com.statussungai.android.data.network.model.AddBiotilikModel
import com.statussungai.android.data.network.model.AddPointModel
import com.statussungai.android.data.network.model.BiotilikResult
import com.statussungai.android.data.network.model.DataItem
import com.statussungai.android.data.network.model.GetUserResponse
import com.statussungai.android.data.network.model.LoginModel
import com.statussungai.android.data.network.model.LoginResponse
import com.statussungai.android.data.network.model.PointsItem
import com.statussungai.android.data.network.model.RegisterModel
import com.statussungai.android.data.network.model.RegisterResponse
import com.statussungai.android.data.network.model.SegmentsItem
import com.statussungai.android.data.network.model.ViewsModel

class Repository(
    private val apiService: ApiService,
    private val authDataStore: AuthDataStore,
    private val filterDataStore: FilterDataStore
) {
    fun getToken(): Flow<String?> = authDataStore.getToken()

    private suspend fun saveToken(token: String) {
        authDataStore.saveToken(token)
    }

    private suspend fun clearToken() {
        authDataStore.clearToken()
    }

    fun getRole(): Flow<String?> = authDataStore.getRole()

    suspend fun saveRole(role: String) {
        authDataStore.saveRole(role)
    }

    private suspend fun clearRole() {
        authDataStore.clearRole()
    }

    fun getUserId(): Flow<Int?> = authDataStore.getUserId()

    private suspend fun saveUserId(userId: Int) {
        authDataStore.saveUserId(userId)
    }

    private suspend fun clearUserId() {
        authDataStore.clearUserId()
    }

    fun getRiverId(): Flow<Int?> = filterDataStore.getRiverId()

    suspend fun saveRiverId(riverId: Int) {
        filterDataStore.saveRiverId(riverId)
    }

    suspend fun clearRiverId() {
        filterDataStore.clearRiverId()
    }

    fun getSeasonId(): Flow<Int?> = filterDataStore.getSeasonId()

    suspend fun saveSeasonId(seasonId: Int) {
        filterDataStore.saveSeasonId(seasonId)
    }

    suspend fun clearSeasonId() {
        filterDataStore.clearSeasonId()
    }

    fun getYear(): Flow<Int?> = filterDataStore.getYear()

    suspend fun saveYear(year: Int) {
        filterDataStore.saveYear(year)
    }

    suspend fun clearYear() {
        filterDataStore.clearYear()
    }

    fun getSortAlphabetically(): Flow<Boolean?> = filterDataStore.getSortAlphabetically()

    suspend fun saveSortAlphabetically(isAscending: Boolean) {
        filterDataStore.saveSortAlphabetically(isAscending)
    }

    suspend fun clearSortAlphabetically() {
        filterDataStore.clearSortAlphabetically()
    }

    fun getSortDate(): Flow<Boolean?> = filterDataStore.getSortDate()

    suspend fun saveSortDate(isAscending: Boolean) {
        filterDataStore.saveSortDate(isAscending)
    }

    suspend fun clearSortDate() {
        filterDataStore.clearSortDate()
    }

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
                        community = community,
                        type = "relawan",
                        active = "active",
                        riverId = 2,
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
            response.data.accessToken.let { saveToken(it) }
            response.data.type.let { saveRole(it) }
            response.data.id.let { saveUserId(it) }
            emit(Resource.Success(response))
        } catch (e: Exception) {
            if (e.message.isNullOrBlank()) {
                emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
            } else {
                emit(Resource.Error(UiText.DynamicString(e.message.toString())))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun logoutUser(): Flow<Resource<UiText>> = flow {
        emit(Resource.Loading)
        try {
            clearToken()
            clearRole()
            clearUserId()
            emit(Resource.Success(UiText.StringResource(R.string.logout)))
        } catch (e: Exception) {
            if (e.message.isNullOrBlank()) {
                emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
            } else {
                emit(Resource.Error(UiText.DynamicString(e.message.toString())))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun segments(): Flow<Resource<List<SegmentsItem>>> = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.segments()
            emit(Resource.Success(response.data.segments))
        } catch (e: Exception) {
            if (e.message.isNullOrBlank()) {
                emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
            } else {
                emit(Resource.Error(UiText.DynamicString(e.message.toString())))
            }
        }
    }.flowOn(Dispatchers.IO)

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

    fun getUserById(userId: String): Flow<Resource<GetUserResponse>> = channelFlow {
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

    private fun generateBearerToken(token: String): String {
        return if (token.contains("bearer", true)) {
            token
        } else {
            "Bearer $token"
        }
    }

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