package com.statussungai.android

import android.app.Application
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.statussungai.android.di.repositoryModule
import com.statussungai.android.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class StatusSungaiApp: Application() {
    override fun onCreate() {
        super.onCreate()

        setupTimber()
        startKoin {
            androidContext(this@StatusSungaiApp)
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}

fun setupTimber() {
    if (BuildConfig.DEBUG) {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String {
                return "~(${element.fileName}:${element.lineNumber})#${element.methodName}"
            }
        })
    } else {
        Timber.plant(CrashReportingTree())
    }
}

class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.log(message)

        if (priority == Log.ERROR) {
            if (t == null) crashlytics.recordException(Throwable(message))
            else crashlytics.recordException(t)
        }
    }
}