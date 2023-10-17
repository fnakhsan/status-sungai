package com.example.core.domain

import com.example.core.data.Resource
import com.example.core.domain.model.PointModel
import com.example.core.domain.model.SegmentModel
import kotlinx.coroutines.flow.Flow

interface ICoreRepository {
    fun getSegments(): Flow<Resource<List<SegmentModel>>>

    fun getPoints(): Flow<Resource<List<PointModel>>>
}