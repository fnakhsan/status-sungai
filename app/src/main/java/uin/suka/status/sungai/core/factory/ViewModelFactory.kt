package uin.suka.status.sungai.core.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uin.suka.status.sungai.data.Repository
import uin.suka.status.sungai.di.Injection
import uin.suka.status.sungai.ui.add.AddViewModel
import uin.suka.status.sungai.ui.login.LoginViewModel
import uin.suka.status.sungai.ui.main.MainViewModel
import uin.suka.status.sungai.ui.maps.MapsViewModel
import uin.suka.status.sungai.ui.point.AddPointViewModel
import uin.suka.status.sungai.ui.register.RegisterViewModel

class ViewModelFactory private constructor(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> return LoginViewModel(
                repository
            ) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> return RegisterViewModel(
                repository
            ) as T
            modelClass.isAssignableFrom(MainViewModel::class.java) -> return MainViewModel(
                repository
            ) as T
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> return MapsViewModel(
                repository
            ) as T
            modelClass.isAssignableFrom(AddViewModel::class.java) -> return AddViewModel(
                repository
            ) as T
            modelClass.isAssignableFrom(AddPointViewModel::class.java) -> return AddPointViewModel(
                repository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}
