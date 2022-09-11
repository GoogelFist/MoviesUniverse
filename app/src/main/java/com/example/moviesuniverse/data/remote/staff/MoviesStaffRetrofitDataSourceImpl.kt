package com.example.moviesuniverse.data.remote.staff

import com.example.moviesuniverse.data.remote.ApiResult
import com.example.moviesuniverse.data.remote.MoviesStaffRemoteDataSource
import com.example.moviesuniverse.domain.models.MovieStaffItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

class MoviesStaffRetrofitDataSourceImpl(private val retrofitService: MoviesStaffRetrofitService) :
    MoviesStaffRemoteDataSource {

    override suspend fun getMovieStaff(movieId: String): Flow<ApiResult<List<MovieStaffItem>>> {
        return flow {
            val response = retrofitService.getMovieStaff(movieId)

            if (response.isSuccessful) {
                emit(
                    ApiResult.Success(
                        response.body()!!.map { it.toMovieStaffItem() }
                    )
                )
            } else {
                emit(ApiResult.Error(HttpException(response)))
            }
        }.catch { e ->
            emit(ApiResult.Error(e))
        }.flowOn(Dispatchers.IO)
    }
}