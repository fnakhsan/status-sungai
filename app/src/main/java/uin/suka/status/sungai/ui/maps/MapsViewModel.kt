package uin.suka.status.sungai.ui.maps

import androidx.lifecycle.ViewModel
import uin.suka.status.sungai.data.Repository

class MapsViewModel(private val repository: Repository) : ViewModel() {
    fun segments() = repository.segments()

    fun views() = repository.views()
}