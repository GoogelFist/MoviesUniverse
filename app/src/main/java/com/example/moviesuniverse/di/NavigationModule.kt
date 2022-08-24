package com.example.moviesuniverse.di

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.moviesuniverse.di.NavigationModule.GLOBAL_QUALIFIER_NAME
import com.example.moviesuniverse.di.NavigationModule.TABS_QUALIFIER_NAME
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.core.qualifier.named
import org.koin.dsl.module

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

    single(named(GLOBAL_QUALIFIER_NAME)) { provideGlobalNavigation() }
    single(named(GLOBAL_QUALIFIER_NAME)) {
        provideGlobalRouter(
            get(
                qualifier = named(
                    GLOBAL_QUALIFIER_NAME
                )
            )
        )
    }
    single(named(GLOBAL_QUALIFIER_NAME)) {
        provideGlobalNavigatorHolder(
            get(
                qualifier = named(
                    GLOBAL_QUALIFIER_NAME
                )
            )
        )
    }
    single(named(GLOBAL_QUALIFIER_NAME)) { provideGlobalAppNavigator(get(), get()) }

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

    single(named(TABS_QUALIFIER_NAME)) { provideTabsNavigation() }
    single(named(TABS_QUALIFIER_NAME)) { provideTabsRouter(get(qualifier = named(TABS_QUALIFIER_NAME))) }
    single(named(TABS_QUALIFIER_NAME)) {
        provideTabsNavigatorHolder(get(qualifier = named(TABS_QUALIFIER_NAME)))
    }
    single(named(TABS_QUALIFIER_NAME)) { provideTabsAppNavigator(get(), get(), get()) }

}

object NavigationModule {
    const val GLOBAL_QUALIFIER_NAME = "Global"
    const val TABS_QUALIFIER_NAME = "Tabs"
}

