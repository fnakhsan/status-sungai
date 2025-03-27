package com.statussungai.android.di.network

import com.statussungai.android.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

fun provideLoggingInterceptor() =
    if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

//fun provideSSL(): CertificatePinner = CertificatePinner.Builder().build()

fun provideHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
): OkHttpClient =
    OkHttpClient
        .Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

val httpModule = module {
    single { provideLoggingInterceptor() }
    single { provideHttpClient(get()) }
}