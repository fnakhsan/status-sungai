package uin.suka.status.sungai.ui.main

import androidx.lifecycle.ViewModel
import uin.suka.status.sungai.data.Repository

class MainViewModel(private val repository: Repository): ViewModel() {
    fun getToken() = repository.getToken()
}