package com.example.moviesuniverse.data.remote.movies

import com.example.moviesuniverse.data.local.movies.models.MovieDetailEntity
import com.example.moviesuniverse.data.remote.ApiResult
import com.example.moviesuniverse.data.remote.MoviesRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

class MoviesRetrofitDataSourceImpl(private val retrofitService: MoviesRetrofitService) :
    MoviesRemoteDataSource {

    override suspend fun getMovieDetail(id: String): Flow<ApiResult<MovieDetailEntity>> {
        return flow {
            val response = retrofitService.getMovieDetail(id)

            if (response.isSuccessful) {
                emit(
                    ApiResult.Success(
                        MovieDetailEntity.fromMovieDetailResponse(response.body()!!)
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