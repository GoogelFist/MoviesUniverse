package com.example.moviesuniverse.presentation.screens

import com.example.moviesuniverse.presentation.screens.splash.SplashFragment
import com.example.moviesuniverse.presentation.screens.tabs.MainFragment
import com.example.moviesuniverse.presentation.screens.tabs.MoviesFragment
import com.example.moviesuniverse.presentation.screens.tabs.StaffFragment
import com.example.moviesuniverse.presentation.screens.tabs.TabsFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun splash() = FragmentScreen { SplashFragment() }
    fun tabs() = FragmentScreen { TabsFragment() }
    fun main() = FragmentScreen { MainFragment() }
    fun movies() = FragmentScreen { MoviesFragment() }
    fun staff() = FragmentScreen { StaffFragment() }
}