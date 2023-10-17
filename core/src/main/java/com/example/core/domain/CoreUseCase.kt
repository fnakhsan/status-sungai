package com.example.core.domain

import android.util.Log
import com.example.core.data.Resource
import com.example.core.domain.model.PointModel
import com.example.core.domain.model.SegmentModel
import com.example.core.utils.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface CoreUseCase {
    fun getSegments(): Flow<Resource<List<SegmentModel>>>

    fun getPoints(): Flow<Resource<List<PointModel>>>

    fun registerUser(
        username: String,
        password: String,
        name: String,
        community: String?
    ): Flow<Resource<RegisterResponse>>

    fun loginUser(username: String, password: String): Flow<Resource<LoginResponse>>

    fun logoutUser(): Flow<Resource<UiText>>

    fun segments(): Flow<Resource<List<SegmentsItem>>>

    fun views(): Flow<Resource<ViewsModel>>

    fun getAllPoints(): Flow<Resource<List<PointsItem>>>

    fun getPointById(pointId: String): Flow<Resource<PointsItem>>

    fun getStatusByPointId(pointId: String): Flow<Resource<List<DataItem>>>

    fun addPoint(name: String, latitude: Double, longitude: Double)

    fun addBiotilik(
        pointId: String,
        biotilikResult: BiotilikResult,
        seasonType: SeasonType,
        year: Int
    )

    fun getUserById(userId: String): Flow<Resource<GetUserResponse>>
}