package com.statussungai.android.ui.point

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import com.statussungai.android.data.Repository

class AddPointViewModel(private val repository: Repository) : ViewModel() {
    fun addPoint(name: String, latitude: Double, longitude: Double) =
        repository.addPoint(name, latitude, longitude).asLiveData(Dispatchers.IO)
}