package uin.suka.status.sungai.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import uin.suka.status.sungai.core.utils.FragmentUtil
import uin.suka.status.sungai.data.Repository

class ProfileViewModel(private val repository: Repository): ViewModel() {
    fun getUserId() = repository.getUserId().asLiveData(Dispatchers.IO)
    fun getUserById(userId: String) = repository.getUserById(userId).asLiveData(Dispatchers.IO)

    fun logoutUser() = repository.logoutUser().asLiveData(Dispatchers.IO)

    fun getFragmentData() = repository.getFragmentData().asLiveData(Dispatchers.IO)

    fun setFragmentData(fragmentUtil: FragmentUtil) = repository.setFragmentData(fragmentUtil)
}