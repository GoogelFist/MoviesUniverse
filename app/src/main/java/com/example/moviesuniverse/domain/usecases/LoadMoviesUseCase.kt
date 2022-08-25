package com.example.moviesuniverse.domain.usecases

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.moviesuniverse.data.remote.movies.models.MovieItemResponse
import com.example.moviesuniverse.domain.MoviesRepository
import com.example.moviesuniverse.domain.models.MovieItem

class LoadMoviesUseCase (private val moviesRepository: MoviesRepository) {
    operator fun invoke(): LiveData<PagingData<MovieItemResponse.Film>> {
       return moviesRepository.getAllMovies()
    }
}