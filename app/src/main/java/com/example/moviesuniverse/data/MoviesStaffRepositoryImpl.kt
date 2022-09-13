package com.example.moviesuniverse.data

import com.example.moviesuniverse.data.local.DaoResult
import com.example.moviesuniverse.data.local.MovieStaffLocalDataSource
import com.example.moviesuniverse.data.remote.ApiResult
import com.example.moviesuniverse.data.remote.MovieStaffRemoteDataSource
import com.example.moviesuniverse.domain.MoviesStaffRepository
import com.example.moviesuniverse.domain.models.MovieStaffItem
import com.example.moviesuniverse.domain.models.StaffDetail
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MoviesStaffRepositoryImpl(
    private val remoteDataSource: MovieStaffRemoteDataSource,
    private val localDataSource: MovieStaffLocalDataSource
) : MoviesStaffRepository {

    @OptIn(FlowPreview::class)
    override suspend fun getMovieStaff(movieId: String): Flow<ApiResult<List<MovieStaffItem>>> {
        return localDataSource.getMovieStaff(movieId).flatMapConcat { daoResult ->
            if (daoResult is DaoResult.Exist) {
                flow { emit(ApiResult.Success(daoResult.data)) }
            } else {
                getMoveStaffApiFlow(movieId)
            }
        }
    }

    @OptIn(FlowPreview::class)
    private suspend fun getMoveStaffApiFlow(movieId: String): Flow<ApiResult<List<MovieStaffItem>>> {
        return remoteDataSource.getMovieStaff(movieId).flatMapConcat { response ->
            when (response) {
                is ApiResult.Error -> flow { emit(ApiResult.Error(response.error)) }
                is ApiResult.Success -> {
                    localDataSource.insertAllMoviesStaff(response.data)

                    flow {
                        localDataSource.getMovieStaff(movieId).map { daoResult ->
                            if (daoResult is DaoResult.Exist) {
                                ApiResult.Success(daoResult.data)
                            } else {
                                emit(ApiResult.Error(RuntimeException(EXCEPTION_MESSAGE)))
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    override suspend fun getStaffDetail(staffId: String): Flow<ApiResult<StaffDetail>> {
        return localDataSource.getStaffDetail(staffId).flatMapConcat { daoResult ->
            if (daoResult is DaoResult.Exist) {
                flow { emit(ApiResult.Success(daoResult.data)) }
            } else {
                getStaffDetailApiFlow(staffId)
            }
        }
    }

    @OptIn(FlowPreview::class)
    private suspend fun getStaffDetailApiFlow(staffId: String): Flow<ApiResult<StaffDetail>> {
        return remoteDataSource.getStaffDetail(staffId).flatMapConcat { response ->
            when (response) {
                is ApiResult.Error -> flow { emit(ApiResult.Error(response.error)) }
                is ApiResult.Success -> {
                    localDataSource.insertStaffDetail(response.data)

                    flow {
                        localDataSource.getStaffDetail(staffId).map { daoResult ->
                            if (daoResult is DaoResult.Exist) {
                                ApiResult.Success(daoResult.data)
                            } else {
                                emit(ApiResult.Error(RuntimeException(EXCEPTION_MESSAGE)))
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val EXCEPTION_MESSAGE = "Entity not exist in base after insert"
    }
}