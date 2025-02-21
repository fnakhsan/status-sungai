package com.statussungai.android.ui.main

import androidx.lifecycle.ViewModel
import com.statussungai.android.data.Repository

class MainViewModel(private val repository: Repository): ViewModel() {
    fun getToken() = repository.getToken()
}