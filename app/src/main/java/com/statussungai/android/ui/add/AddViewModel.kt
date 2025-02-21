package com.statussungai.android.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.statussungai.android.data.Repository

class AddViewModel(private val repository: Repository): ViewModel() {
    fun getAllPoints() = repository.getAllPoints().asLiveData(Dispatchers.IO)

    fun getToken() = repository.getToken().asLiveData(Dispatchers.IO)

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

    fun getSortAlphabetically() = repository.getSortAlphabetically().asLiveData(Dispatchers.IO)

    fun saveSortAlphabetically(isAscending: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveSortAlphabetically(isAscending)
        }
    }

    fun clearSortAlphabetically() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearSortAlphabetically()
        }
    }

    fun getSortDate() = repository.getSortDate().asLiveData(Dispatchers.IO)

    fun saveSortDate(isAscending: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveSortDate(isAscending)
        }
    }

    fun clearSortDate() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearSortDate()
        }
    }
}