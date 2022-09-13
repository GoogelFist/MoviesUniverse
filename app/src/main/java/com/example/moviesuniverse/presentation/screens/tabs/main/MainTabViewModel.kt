package com.example.moviesuniverse.presentation.screens.tabs.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.moviesuniverse.domain.models.MovieItem
import com.example.moviesuniverse.domain.usecases.LoadMoviesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MainTabViewModel(private val loadMoviesUseCase: LoadMoviesUseCase) : ViewModel() {

    fun getMovieList(): Flow<PagingData<MovieItem>> {
        return loadMoviesUseCase()
            .map { pagingData -> pagingData.map { movieEntity -> movieEntity.toMovieItem() } }
            .cachedIn(viewModelScope)
    }
}