package com.mertkahraman.themusicbox

import android.app.Application
import com.mertkahraman.themusicbox.di.appModule
import com.mertkahraman.themusicbox.di.networkingModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TmbApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TmbApp)
            modules(listOf(appModule(), networkingModule()))
        }
    }
}
