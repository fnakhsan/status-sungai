package com.example.core.domain

import com.example.core.data.Resource
import com.example.core.data.remote.response.AddBiotilikModel
import com.example.core.domain.model.AddPointModel
import com.example.core.domain.model.LoginModel
import com.example.core.domain.model.PointModel
import com.example.core.domain.model.RegisterModel
import com.example.core.domain.model.SegmentModel
import com.example.core.domain.model.StatusModel
import com.example.core.domain.model.UserModel
import com.example.core.utils.UiText
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoreInteractor @Inject constructor(private val coreRepository: ICoreRepository) :
    CoreUseCase {
    override fun registerUser(registerModel: RegisterModel): Flow<Resource<UiText>> {
        return coreRepository.registerUser(registerModel)
    }

    override fun loginUser(loginModel: LoginModel): Flow<Resource<UiText>> {
       return coreRepository.loginUser(loginModel)
    }

    override fun logoutUser(): Flow<Resource<UiText>> {
        return coreRepository.logoutUser()
    }

    override fun getUserById(userId: String): Flow<Resource<UserModel>> {
        return coreRepository.getUserById(userId)
    }

    override fun getSegments(): Flow<Resource<SegmentModel>> {
        return coreRepository.getSegments()
    }

    override fun getPoints(): Flow<Resource<List<PointModel>>> {
        return coreRepository.getPoints()
    }

    override fun getPointById(pointId: String): Flow<Resource<PointModel>> {
        return coreRepository.getPointById(pointId)
    }

    override fun getStatusByPointId(pointId: String): Flow<Resource<List<StatusModel>>> {
        return coreRepository.getStatusByPointId(pointId)
    }

    override fun addPoint(addPointModel: AddPointModel): Flow<Resource<UiText>> {
        return coreRepository.addPoint(addPointModel)
    }

    override fun addBiotilik(addBiotilikModel: AddBiotilikModel): Flow<Resource<UiText>> {
        return coreRepository.addBiotilik(addBiotilikModel)
    }
}