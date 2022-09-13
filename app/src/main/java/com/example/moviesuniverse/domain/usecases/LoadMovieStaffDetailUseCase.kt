package com.example.moviesuniverse.domain.usecases

import com.example.moviesuniverse.data.remote.ApiResult
import com.example.moviesuniverse.domain.MoviesStaffRepository
import com.example.moviesuniverse.domain.models.MovieStaffDetail
import kotlinx.coroutines.flow.Flow

class LoadMovieStaffDetailUseCase(private val moviesStaffRepository: MoviesStaffRepository) {
    suspend operator fun invoke(staffId: String): Flow<ApiResult<MovieStaffDetail>> {
        return moviesStaffRepository.getStaffDetail(staffId)
    }
}