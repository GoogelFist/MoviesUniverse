package com.example.moviesuniverse

import android.app.Application
import com.example.moviesuniverse.di.*
import com.example.moviesuniverse.di.data.moviesDataModule
import com.example.moviesuniverse.di.data.retrofitModule
import com.example.moviesuniverse.di.data.roomModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            modules(
                listOf(
                    applicationModule,
                    globalNavigationModule,
                    tabsNavigationModule,
                    singleTabNavigationModule,
                    moviesDataModule,
                    domainModule,
                    retrofitModule,
                    roomModule
                )
            )
        }
    }
}