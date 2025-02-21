package com.statussungai.android.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import com.statussungai.android.data.Repository

class ProfileViewModel(private val repository: Repository): ViewModel() {
    fun getUserId() = repository.getUserId().asLiveData(Dispatchers.IO)
    fun getUserById(userId: String) = repository.getUserById(userId).asLiveData(Dispatchers.IO)

    fun logoutUser() = repository.logoutUser().asLiveData(Dispatchers.IO)
}