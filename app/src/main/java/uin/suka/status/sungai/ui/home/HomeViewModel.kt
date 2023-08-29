package uin.suka.status.sungai.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import uin.suka.status.sungai.data.Repository

class HomeViewModel(private val repository: Repository) : ViewModel() {

    fun getFragmentData() = repository.getFragmentData().asLiveData(Dispatchers.IO)
}