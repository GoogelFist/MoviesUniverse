package com.example.moviesuniverse.presentation.screens

import com.example.moviesuniverse.presentation.screens.splash.SplashFragment
import com.example.moviesuniverse.presentation.screens.tabs.main.MainTabFragment
import com.example.moviesuniverse.presentation.screens.tabs.MoviesFragment
import com.example.moviesuniverse.presentation.screens.tabs.StaffFragment
import com.example.moviesuniverse.presentation.screens.tabs.TabsFragment
import com.example.moviesuniverse.presentation.screens.tabs.detail.MovieDetailFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun splash() = FragmentScreen { SplashFragment() }
    fun tabs() = FragmentScreen { TabsFragment() }
    fun main() = FragmentScreen { MainTabFragment() }
    fun movies() = FragmentScreen { MoviesFragment() }
    fun staff() = FragmentScreen { StaffFragment() }

    fun detailMovie(id: String) = FragmentScreen { MovieDetailFragment.newInstance(id) }
}