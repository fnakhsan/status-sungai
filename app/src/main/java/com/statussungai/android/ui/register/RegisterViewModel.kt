package com.statussungai.android.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.statussungai.android.data.Repository

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    fun registerUser(
        username: String,
        password: String,
        name: String,
        community: String?
    ) = repository.registerUser(
        username = username,
        password = password,
        name = name,
        community = community
    )

    fun setRole(role: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveRole(role)
    }
}