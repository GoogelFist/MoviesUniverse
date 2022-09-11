package com.example.moviesuniverse.data.remote

import com.example.moviesuniverse.domain.models.MovieStaffItem
import kotlinx.coroutines.flow.Flow

interface MoviesStaffRemoteDataSource {

    suspend fun getMovieStaff(movieId: String): Flow<ApiResult<List<MovieStaffItem>>>
}