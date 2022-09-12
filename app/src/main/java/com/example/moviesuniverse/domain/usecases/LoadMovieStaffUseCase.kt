package com.example.moviesuniverse.domain.usecases

import com.example.moviesuniverse.data.remote.ApiResult
import com.example.moviesuniverse.domain.MoviesStaffRepository
import com.example.moviesuniverse.domain.models.MovieStaffItem
import kotlinx.coroutines.flow.Flow

class LoadMovieStaffUseCase (private val moviesStaffRepository: MoviesStaffRepository) {
    suspend operator fun invoke(movieId: String): Flow<ApiResult<List<MovieStaffItem>>> {
       return moviesStaffRepository.getMovieStaff(movieId)
    }
}