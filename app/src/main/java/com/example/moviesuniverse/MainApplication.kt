package com.example.moviesuniverse

import android.app.Application
import com.example.moviesuniverse.di.*
import com.example.moviesuniverse.di.data.dataModule
import com.example.moviesuniverse.di.data.retrofitModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            modules(
                listOf(
                    applicationModule,
                    globalNavigationModule,
                    tabsNavigationModule,
                    dataModule,
                    domainModule,
                    retrofitModule
                )
            )
        }
    }
}