package com.lambao.mrbeast

import android.app.Application
import com.lambao.core.di.coreModule
import com.lambao.core.network.di.networkModule
import com.lambao.data.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        
        // Initialize Koin
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyApplication)
            modules(
                coreModule,
                networkModule,
                dataModule
                // Add more modules here as you create them
            )
        }
    }
}