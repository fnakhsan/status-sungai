package uin.suka.status.sungai.ui.add

import androidx.lifecycle.ViewModel
import uin.suka.status.sungai.data.Repository

class AddViewModel(private val repository: Repository): ViewModel() {
    fun getPoint() = repository.getPoint()

    fun getToken() = repository.getToken()
}