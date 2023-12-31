package com.example.core.utils

import com.example.core.data.remote.response.AddBiotilikBody
import com.example.core.data.remote.response.AddPointBody
import com.example.core.data.remote.response.BiotilikResult
import com.example.core.data.remote.response.DetailsItem
import com.example.core.data.remote.response.LoginBody
import com.example.core.data.remote.response.PointsItem
import com.example.core.data.remote.response.RegisterBody
import com.example.core.data.remote.response.RiversItem
import com.example.core.data.remote.response.StatusItem
import com.example.core.data.remote.response.ViewPointsItem
import com.example.core.domain.model.AddBiotilikModel
import com.example.core.domain.model.AddPointModel
import com.example.core.domain.model.LoginModel
import com.example.core.domain.model.PointModel
import com.example.core.domain.model.RegisterModel
import com.example.core.domain.model.SegmentModel
import com.example.core.domain.model.FlowPointModel
import com.example.core.domain.model.StatusModel
import com.example.core.domain.model.ViewPointModel

object DataMappers {

    fun RegisterModel.toBody(): RegisterBody {
        return RegisterBody(
            password = password,
            riverId = 1,
            name = name,
            community = community,
            active = "active",
            type = "relawan",
            username = username
        )
    }

    fun LoginModel.toBody(): LoginBody {
        return LoginBody(
            username = username,
            password = password
        )
    }

    fun List<RiversItem>.toModel(): List<SegmentModel> {
        return flatMap { river ->
            river.segments.map {
                SegmentModel(
                    id = it.id,
                    name = it.name,
                    active = it.active,
                    points = it.points.toModel(),
                    flowPoints = it.details.toModel()
                )
            }
        }
    }

    fun List<ViewPointsItem>.toModel(): List<ViewPointModel> {
        return map {
            ViewPointModel(
                id = it.id,
                latitude = it.latitude,
                longitude = it.longitude,
                status = it.datas.firstNotNullOf { data ->
                    data.status
                }
            )
        }
    }

    fun List<DetailsItem>.toModel(): List<FlowPointModel> {
        return map {
            FlowPointModel (
                id = it.id,
                latitude = it.latitude,
                longitude = it.longitude
            )
        }
    }

    fun List<PointsItem>.toModel(): List<PointModel> {
        return map {
            PointModel(
                id = it.id,
                name = it.name,
                riverId = it.riverId,
                segmentId = it.segmentId,
                createdBy = it.userName,
                latitude = it.latitude,
                longitude = it.longitude
            )
        }
    }

    fun List<StatusItem?>?.toModel(): List<StatusModel> {
        return this?.map {
            StatusModel(
                seasonId = it?.seasonId ?: 1,
                year = it?.year ?: 2023,
                status = it?.status,
                date = it?.updatedAt ?: System.currentTimeMillis().toString(),
                familyType = it?.result?.jenisFamili,
                eptType = it?.result?.jenisEpt,
                eptPercentage = it?.result?.persenEpt,
                biotilikIndex = it?.result?.indeksBiotilik
            )
        } ?: emptyList()
    }

    fun AddPointModel.toBody(): AddPointBody {
        return AddPointBody(
            name = name,
            active = "active",
            latitude = latitude.toDouble(),
            longitude = longitude.toDouble()
        )
    }

    fun AddBiotilikModel.toBody(): AddBiotilikBody {
        return AddBiotilikBody(
            method = "biotilik",
            data = BiotilikResult(
                persenEpt = eptPercentage,
                indeksBiotilik = biotilikIndex,
                jenisFamili = familyType,
                jenisEpt = eptType
            ),
            seasonId = seasonId,
            year = year
        )
    }
}