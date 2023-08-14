package uin.suka.status.sungai.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import uin.suka.status.sungai.data.Repository

class AddViewModel(private val repository: Repository): ViewModel() {
    fun getAllPoints() = repository.getAllPoints().asLiveData(Dispatchers.IO)

    fun getToken() = repository.getToken().asLiveData(Dispatchers.IO)
}