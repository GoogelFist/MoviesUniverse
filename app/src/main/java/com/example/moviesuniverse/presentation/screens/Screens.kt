package com.example.moviesuniverse.presentation.screens

import androidx.annotation.StringRes
import com.example.moviesuniverse.presentation.screens.splash.SplashFragment
import com.example.moviesuniverse.presentation.screens.tabs.TabsFragment
import com.example.moviesuniverse.presentation.screens.tabs.detail.MovieDetailFragment
import com.example.moviesuniverse.presentation.screens.tabs.main.MainTabFragment
import com.example.moviesuniverse.presentation.screens.tabs.search.MoviesSearchTabFragment
import com.example.moviesuniverse.presentation.screens.tabs.sraff.MovieStaffFragment
import com.example.moviesuniverse.presentation.screens.tabs.sraff.detail.StaffDetailFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun splash() = FragmentScreen { SplashFragment() }
    fun tabs() = FragmentScreen { TabsFragment() }
    fun mainTab() = FragmentScreen { MainTabFragment() }
    fun moviesSearchTab() = FragmentScreen { MoviesSearchTabFragment() }
    fun movieStaff(movieId: String) = FragmentScreen { MovieStaffFragment.newInstance(movieId) }

    fun detailMovie(id: String, @StringRes title: Int) =
        FragmentScreen { MovieDetailFragment.newInstance(id, title) }
    fun detailStaff(staffId: String) =
        FragmentScreen { StaffDetailFragment.newInstance(staffId) }
}