package com.statussungai.android.ui.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.statussungai.android.data.Repository

class MapsViewModel(private val repository: Repository) : ViewModel() {

    fun views() = repository.views().asLiveData(Dispatchers.IO)


    fun getRole() = repository.getRole().asLiveData(Dispatchers.IO)

    fun getRiverId() = repository.getRiverId().asLiveData(Dispatchers.IO)

    fun setRiverId(riverId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveRiverId(riverId)
        }
    }

    fun clearRiverId() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearRiverId()
        }
    }

    fun getSeasonId() = repository.getSeasonId().asLiveData(Dispatchers.IO)

    fun setSeasonId(seasonId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveSeasonId(seasonId)
        }
    }

    fun clearSeasonId() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearSeasonId()
        }
    }

    fun getYear() = repository.getYear().asLiveData(Dispatchers.IO)

    fun setYear(year: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveYear(year)
        }
    }

    fun clearYear() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearYear()
        }
    }
}