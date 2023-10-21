package com.example.core.domain

import com.example.core.data.Resource
import com.example.core.domain.model.AddBiotilikModel
import com.example.core.domain.model.AddPointModel
import com.example.core.domain.model.LoginModel
import com.example.core.domain.model.PointModel
import com.example.core.domain.model.RegisterModel
import com.example.core.domain.model.SegmentModel
import com.example.core.domain.model.StatusModel
import com.example.core.domain.model.UserModel
import com.example.core.utils.UiText
import kotlinx.coroutines.flow.Flow

interface CoreUseCase {
    fun registerUser(registerModel: RegisterModel): Flow<Resource<UiText>>

    fun loginUser(loginModel: LoginModel): Flow<Resource<UiText>>

    fun logoutUser(): Flow<Resource<UiText>>

    fun getUserById(userId: String): Flow<Resource<UserModel>>

    fun getSegments(): Flow<Resource<List<SegmentModel>>>

    fun getPoints(token: String): Flow<Resource<List<PointModel>>>

    fun getPointById(token: String, pointId: String): Flow<Resource<PointModel>>

    fun getStatusByPointId(token: String, pointId: String): Flow<Resource<List<StatusModel>>>

    fun addPoint(token: String, addPointModel: AddPointModel): Flow<Resource<UiText>>

    fun addBiotilik(token: String, pointId: String, addBiotilikModel: AddBiotilikModel): Flow<Resource<UiText>>
}