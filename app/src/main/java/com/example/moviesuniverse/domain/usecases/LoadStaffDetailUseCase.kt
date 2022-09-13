package com.example.moviesuniverse.domain.usecases

import com.example.moviesuniverse.data.remote.ApiResult
import com.example.moviesuniverse.domain.MoviesStaffRepository
import com.example.moviesuniverse.domain.models.StaffDetail
import kotlinx.coroutines.flow.Flow

class LoadStaffDetailUseCase(private val moviesStaffRepository: MoviesStaffRepository) {
    suspend operator fun invoke(staffId: String): Flow<ApiResult<StaffDetail>> {
        return moviesStaffRepository.getStaffDetail(staffId)
    }
}