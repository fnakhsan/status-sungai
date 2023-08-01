package uin.suka.status.sungai.ui.login

import androidx.lifecycle.ViewModel
import uin.suka.status.sungai.data.Repository

class LoginViewModel(private val repository: Repository) : ViewModel() {
    fun loginUser(email: String, password: String) =
        repository.loginUser(email, password)
}