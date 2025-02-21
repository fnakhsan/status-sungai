package com.statussungai.android.ui.biotilik

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import com.statussungai.android.core.utils.SeasonType
import com.statussungai.android.data.Repository
import com.statussungai.android.data.network.model.BiotilikResult

class AddBiotilikViewModel(private val repository: Repository) : ViewModel() {
    fun addBiotilik(
        pointId: String,
        biotilikResult: BiotilikResult,
        seasonType: SeasonType,
        year: Int
    ) = repository.addBiotilik(pointId, biotilikResult, seasonType, year).asLiveData(Dispatchers.IO)
}