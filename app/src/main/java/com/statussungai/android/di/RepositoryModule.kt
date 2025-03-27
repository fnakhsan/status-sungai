package com.statussungai.android.di

import com.statussungai.android.data.Repository
import com.statussungai.android.di.local.dataStoreModule
import com.statussungai.android.di.network.apiModule
import org.koin.dsl.module

val repositoryModule = module {
    includes(apiModule, dataStoreModule)
    single {
        Repository(get(), get(), get())
    }
}