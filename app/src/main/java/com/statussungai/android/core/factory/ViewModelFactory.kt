package com.statussungai.android.core.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.statussungai.android.data.Repository
import com.statussungai.android.di.Injection
import com.statussungai.android.ui.add.AddViewModel
import com.statussungai.android.ui.biotilik.AddBiotilikViewModel
import com.statussungai.android.ui.details.DetailsViewModel
import com.statussungai.android.ui.login.LoginViewModel
import com.statussungai.android.ui.main.MainViewModel
import com.statussungai.android.ui.maps.MapsViewModel
import com.statussungai.android.ui.point.AddPointViewModel
import com.statussungai.android.ui.profile.ProfileViewModel
import com.statussungai.android.ui.register.RegisterViewModel

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
            modelClass.isAssignableFrom(DetailsViewModel::class.java) -> return DetailsViewModel(
                repository
            ) as T
            modelClass.isAssignableFrom(AddPointViewModel::class.java) -> return AddPointViewModel(
                repository
            ) as T
            modelClass.isAssignableFrom(AddBiotilikViewModel::class.java) -> return AddBiotilikViewModel(
                repository
            ) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> return ProfileViewModel(
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
