package com.example.moviesuniverse.domain.usecases

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.moviesuniverse.domain.MoviesRepository
import com.example.moviesuniverse.domain.models.MovieItem

class LoadMoviesUseCase (private val moviesRepository: MoviesRepository) {
    operator fun invoke(): LiveData<PagingData<MovieItem>> {
       return moviesRepository.getTop250Movies()
    }
}