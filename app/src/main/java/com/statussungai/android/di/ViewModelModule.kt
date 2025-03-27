package com.statussungai.android.di

import com.statussungai.android.ui.add.AddViewModel
import com.statussungai.android.ui.biotilik.AddBiotilikViewModel
import com.statussungai.android.ui.details.DetailsViewModel
import com.statussungai.android.ui.login.LoginViewModel
import com.statussungai.android.ui.main.MainViewModel
import com.statussungai.android.ui.maps.MapsViewModel
import com.statussungai.android.ui.point.AddPointViewModel
import com.statussungai.android.ui.profile.ProfileViewModel
import com.statussungai.android.ui.register.RegisterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { MapsViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { AddViewModel(get()) }
    viewModel { AddPointViewModel(get()) }
    viewModel { AddBiotilikViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
}