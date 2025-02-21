package com.statussungai.android.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import com.statussungai.android.data.Repository

class DetailsViewModel(private val repository: Repository) : ViewModel() {
    fun getStatusByPointId(pointId: String) =
        repository.getStatusByPointId(pointId).asLiveData(Dispatchers.IO)

    fun getPointById(pointId: String) = repository.getPointById(pointId).asLiveData(Dispatchers.IO)
}