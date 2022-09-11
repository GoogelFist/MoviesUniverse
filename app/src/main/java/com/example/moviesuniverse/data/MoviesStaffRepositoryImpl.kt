package com.example.moviesuniverse.data

import com.example.moviesuniverse.data.remote.ApiResult
import com.example.moviesuniverse.data.remote.MoviesStaffRemoteDataSource
import com.example.moviesuniverse.domain.MoviesStaffRepository
import com.example.moviesuniverse.domain.models.MovieStaffDetail
import com.example.moviesuniverse.domain.models.MovieStaffItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class MoviesStaffRepositoryImpl(private val moviesStaffRemoteDataSource: MoviesStaffRemoteDataSource) :
    MoviesStaffRepository {

    // TODO:
    override suspend fun getMovieStaff(movieId: String): Flow<ApiResult<List<MovieStaffItem>>> {
        return moviesStaffRemoteDataSource.getMovieStaff(movieId)
    }

    // TODO:
    override suspend fun getStaffDetail(staffId: String): Flow<ApiResult<MovieStaffDetail>> {
        return emptyFlow()
    }
}