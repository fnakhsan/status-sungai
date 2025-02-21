package com.statussungai.android.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import com.statussungai.android.data.Repository

class LoginViewModel(private val repository: Repository) : ViewModel() {
    fun loginUser(email: String, password: String) =
        repository.loginUser(email, password).asLiveData(Dispatchers.IO)
}