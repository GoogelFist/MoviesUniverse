package com.example.moviesuniverse

import android.app.Application
import com.github.terrakok.cicerone.Cicerone

class MainApplication: Application() {
    private val globalNavigation = Cicerone.create()
    val globalRouter get() = globalNavigation.router
    val globalNavigatorHolder get() = globalNavigation.getNavigatorHolder()

    private val tabsNavigation = Cicerone.create()
    val tabsRouter get() = tabsNavigation.router
    val tabsNavigatorHolder get() = tabsNavigation.getNavigatorHolder()

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        internal lateinit var INSTANCE: MainApplication
            private set
    }
}