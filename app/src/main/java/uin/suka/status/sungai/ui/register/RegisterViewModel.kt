package uin.suka.status.sungai.ui.register

import androidx.lifecycle.ViewModel
import uin.suka.status.sungai.data.Repository

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    fun registerUser(
        username: String,
        password: String,
        name: String,
        community: String?
    ) = repository.registerUser(
        username = username,
        password = password,
        name = name,
        community = community
    )
}