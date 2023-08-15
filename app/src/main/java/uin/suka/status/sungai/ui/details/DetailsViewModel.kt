package uin.suka.status.sungai.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import uin.suka.status.sungai.data.Repository

class DetailsViewModel(private val repository: Repository) : ViewModel() {
    fun getStatusByPointId(pointId: String) =
        repository.getStatusByPointId(pointId).asLiveData(Dispatchers.IO)
}