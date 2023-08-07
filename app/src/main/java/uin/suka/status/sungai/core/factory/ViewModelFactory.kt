package uin.suka.status.sungai.core.factory

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import uin.suka.status.sungai.data.Repository
import uin.suka.status.sungai.di.Injection
import uin.suka.status.sungai.ui.login.LoginViewModel
import uin.suka.status.sungai.ui.main.MainViewModel
import uin.suka.status.sungai.ui.maps.MapsViewModel
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
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        private val mutex = Mutex()
        suspend fun getInstance(context: Context): ViewModelFactory =
            instance ?: mutex.withLock {
                Log.d("login", "6")
                withContext(Dispatchers.IO) {
                    Log.d("login", "7")
                    instance ?: ViewModelFactory(Injection.provideRepository(context))
                }
            }.also { instance = it }

//        suspend fun getInstance(context: Context): ViewModelFactory =
//            instance ?: synchronized(this) {
//                instance ?: ViewModelFactory(Injection.provideRepository(context))
//            }.also { instance = it }
    }
}
