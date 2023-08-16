package uin.suka.status.sungai.ui.biotilik

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import uin.suka.status.sungai.core.utils.SeasonType
import uin.suka.status.sungai.data.Repository
import uin.suka.status.sungai.data.network.model.BiotilikResult

class AddBiotilikViewModel(private val repository: Repository) : ViewModel() {
    fun addBiotilik(
        pointId: String,
        biotilikResult: BiotilikResult,
        seasonType: SeasonType,
        year: Int
    ) = repository.addBiotilik(pointId, biotilikResult, seasonType, year).asLiveData(Dispatchers.IO)
}