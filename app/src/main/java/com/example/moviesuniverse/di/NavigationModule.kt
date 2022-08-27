package com.example.moviesuniverse.di

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.core.qualifier.named
import org.koin.dsl.module


const val GLOBAL_QUALIFIER = "Global"
const val TABS_QUALIFIER = "Tabs"

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

    fun provideGlobalAppNavigator(activity: FragmentActivity, containerId: Int): AppNavigator {
        return AppNavigator(activity, containerId)
    }

    single(named(GLOBAL_QUALIFIER)) { provideGlobalNavigation() }
    single(named(GLOBAL_QUALIFIER)) {
        provideGlobalRouter(navigation = get(qualifier = named(GLOBAL_QUALIFIER)))
    }
    single(named(GLOBAL_QUALIFIER)) {
        provideGlobalNavigatorHolder(navigation = get(qualifier = named(GLOBAL_QUALIFIER)))
    }
    single(named(GLOBAL_QUALIFIER)) {
        provideGlobalAppNavigator(activity = get(), containerId = get())
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

    fun provideTabsAppNavigator(
        activity: FragmentActivity,
        containerId: Int,
        fragmentManager: FragmentManager
    ): AppNavigator {
        return AppNavigator(activity, containerId, fragmentManager)
    }

    single(named(TABS_QUALIFIER)) { provideTabsNavigation() }
    single(named(TABS_QUALIFIER)) {
        provideTabsRouter(navigation = get(qualifier = named(TABS_QUALIFIER)))
    }
    single(named(TABS_QUALIFIER)) {
        provideTabsNavigatorHolder(navigation = get(qualifier = named(TABS_QUALIFIER)))
    }
    single(named(TABS_QUALIFIER)) {
        provideTabsAppNavigator(activity = get(), containerId = get(), fragmentManager = get())
    }
}

