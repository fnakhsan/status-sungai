package uin.suka.status.sungai.ui.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uin.suka.status.sungai.core.utils.FragmentUtil
import uin.suka.status.sungai.data.Repository

class MapsViewModel(private val repository: Repository) : ViewModel() {
//    private val _riverId = MutableLiveData<Int?>()
//    val riverId: LiveData<Int?> = _riverId
//    private val _seasonId = MutableLiveData<Int?>()
//    val seasonId: LiveData<Int?> = _seasonId
//    private val _year = MutableLiveData<Int?>()
//    val year: LiveData<Int?> = _year
    fun segments() = repository.segments()

//    fun views(): LiveData<Resource<ViewsModel>> {
//        Log.d("filter", "views: ${_riverId.value} , ${_seasonId.value} , ${_year.value}")
//        return repository.views(_riverId.value, _seasonId.value, _year.value)
//            .asLiveData(Dispatchers.IO)
//    }

    fun views() = repository.views().asLiveData(Dispatchers.IO)


    fun getRole() = repository.getRole().asLiveData(Dispatchers.IO)

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

    fun getSeasonId() = repository.getSeasonId().asLiveData(Dispatchers.IO)

    fun setSeasonId(seasonId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveSeasonId(seasonId)
        }
    }

    fun clearSeasonId() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearSeasonId()
        }
    }

    fun getYear() = repository.getYear().asLiveData(Dispatchers.IO)

    fun setYear(year: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveYear(year)
        }
    }

    fun clearYear() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearYear()
        }
    }

    fun getFragmentData() = repository.getFragmentData().asLiveData(Dispatchers.IO)

    fun setFragmentData(fragmentUtil: FragmentUtil) = repository.setFragmentData(fragmentUtil)
}