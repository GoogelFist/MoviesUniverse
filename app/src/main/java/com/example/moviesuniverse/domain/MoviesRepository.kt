package com.example.moviesuniverse.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.moviesuniverse.data.remote.movies.models.MovieItemResponse
import com.example.moviesuniverse.domain.models.MovieItem

interface MoviesRepository {
    suspend fun loadMovies(): List<MovieItem>

    fun getAllMovies(): LiveData<PagingData<MovieItemResponse.Film>>
}