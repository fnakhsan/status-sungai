package com.example.core.domain

import com.example.core.data.Resource
import com.example.core.domain.model.PointModel
import com.example.core.domain.model.SegmentModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoreInteractor @Inject constructor(private val coreRepository: ICoreRepository) :
    CoreUseCase {
    override fun getSegments(): Flow<Resource<List<SegmentModel>>> {
        return coreRepository.getSegments()
    }

    override fun getPoints(): Flow<Resource<List<PointModel>>> {
        return coreRepository.getPoints()
    }
}