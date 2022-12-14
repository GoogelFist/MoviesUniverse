package com.example.moviesuniverse.domain

import com.example.moviesuniverse.data.remote.ApiResult
import com.example.moviesuniverse.domain.models.StaffDetail
import com.example.moviesuniverse.domain.models.MovieStaffItem
import kotlinx.coroutines.flow.Flow

interface MoviesStaffRepository {

    suspend fun getMovieStaff(movieId: String): Flow<ApiResult<List<MovieStaffItem>>>

    suspend fun getStaffDetail(staffId: String): Flow<ApiResult<StaffDetail>>
}