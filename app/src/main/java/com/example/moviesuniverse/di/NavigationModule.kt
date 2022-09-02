package com.example.moviesuniverse.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import org.koin.core.qualifier.named
import org.koin.dsl.module


const val GLOBAL_QUALIFIER = "Global"
const val TABS_QUALIFIER = "Tabs"
const val SINGLE_TAB_QUALIFIER = "Single_Tab"

val globalNavigationModule = module {

    fun provideGlobalNavigation(): Cicerone<Router> {
        return Cicerone.create()
    }

    fun provideGlobalRouter(navigation: Cicerone<Router>): Router {
        return navigation.router
    }

    fun provideGlobalNavigatorHolder(navigation: Cicerone<Router>): NavigatorHolder {
        return navigation.getNavigatorHolder()
    }

    single(named(GLOBAL_QUALIFIER)) { provideGlobalNavigation() }
    single(named(GLOBAL_QUALIFIER)) {
        provideGlobalRouter(navigation = get(qualifier = named(GLOBAL_QUALIFIER)))
    }
    single(named(GLOBAL_QUALIFIER)) {
        provideGlobalNavigatorHolder(navigation = get(qualifier = named(GLOBAL_QUALIFIER)))
    }
}

val tabsNavigationModule = module {

    fun provideTabsNavigation(): Cicerone<Router> {
        return Cicerone.create()
    }

    fun provideTabsRouter(navigation: Cicerone<Router>): Router {
        return navigation.router
    }

    fun provideTabsNavigatorHolder(navigation: Cicerone<Router>): NavigatorHolder {
        return navigation.getNavigatorHolder()
    }

    single(named(TABS_QUALIFIER)) { provideTabsNavigation() }
    single(named(TABS_QUALIFIER)) {
        provideTabsRouter(navigation = get(qualifier = named(TABS_QUALIFIER)))
    }
    single(named(TABS_QUALIFIER)) {
        provideTabsNavigatorHolder(navigation = get(qualifier = named(TABS_QUALIFIER)))
    }
}

val singleTabNavigationModule = module {

    fun provideSingleTabNavigation(): Cicerone<Router> {
        return Cicerone.create()
    }

    fun provideSingleTabRouter(navigation: Cicerone<Router>): Router {
        return navigation.router
    }

    fun provideSingleTabNavigatorHolder(navigation: Cicerone<Router>): NavigatorHolder {
        return navigation.getNavigatorHolder()
    }

    single(named(SINGLE_TAB_QUALIFIER)) { provideSingleTabNavigation() }
    single(named(SINGLE_TAB_QUALIFIER)) {
        provideSingleTabRouter(navigation = get(qualifier = named(SINGLE_TAB_QUALIFIER)))
    }
    single(named(SINGLE_TAB_QUALIFIER)) {
        provideSingleTabNavigatorHolder(navigation = get(qualifier = named(SINGLE_TAB_QUALIFIER)))
    }
}

