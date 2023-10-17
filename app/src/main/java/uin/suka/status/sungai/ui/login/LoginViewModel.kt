package uin.suka.status.sungai.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.CoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import uin.suka.status.sungai.data.Repository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val coreUseCase: CoreUseCase) : ViewModel() {
    fun loginUser(email: String, password: String) =
        coreUseCase.loginUser(email, password).asLiveData(Dispatchers.IO)
}