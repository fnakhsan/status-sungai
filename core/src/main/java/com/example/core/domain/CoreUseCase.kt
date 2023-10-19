package com.example.core.domain

import android.util.Log
import com.example.core.data.Resource
import com.example.core.data.remote.response.AddBiotilikModel
import com.example.core.data.remote.response.DataItem
import com.example.core.data.remote.response.LoginResponse
import com.example.core.data.remote.response.PointsItem
import com.example.core.data.remote.response.RegisterResponse
import com.example.core.data.remote.response.SegmentsItem
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
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface CoreUseCase {
    fun registerUser(registerModel: RegisterModel): Flow<Resource<UiText>>

    fun loginUser(loginModel: LoginModel): Flow<Resource<UiText>>

    fun logoutUser(): Flow<Resource<UiText>>

    fun getUserById(userId: String): Flow<Resource<UserModel>>

    fun getSegments(): Flow<Resource<List<SegmentModel>>>

    fun getPoints(token: String): Flow<Resource<List<PointModel>>>

    fun getPointById(pointId: String): Flow<Resource<PointModel>>

    fun getStatusByPointId(pointId: String): Flow<Resource<List<StatusModel>>>

    fun addPoint(token: String, addPointModel: AddPointModel): Flow<Resource<UiText>>

    fun addBiotilik(token: String, addBiotilikModel: AddBiotilikModel): Flow<Resource<UiText>>
}