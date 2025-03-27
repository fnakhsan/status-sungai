package com.statussungai.android.di.network

import com.statussungai.android.BuildConfig
import com.statussungai.android.data.network.ApiService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideDeserializer(): GsonConverterFactory = GsonConverterFactory.create()

fun provideRetrofit(
    okHttpClient: OkHttpClient,
    deserializer: GsonConverterFactory
): Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(deserializer)
        .client(okHttpClient)
        .build()

fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

val apiModule = module {
    includes(httpModule)
    single { provideDeserializer() }
    single { provideRetrofit(get(), get()) }
    single { provideApiService(get()) }
}