package com.example.moviesuniverse.data.remote.movies

import com.example.moviesuniverse.data.local.movies.models.MovieDetailEntity
import com.example.moviesuniverse.data.model.ApiResult
import com.example.moviesuniverse.data.remote.MoviesRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class MoviesRetrofitDataSourceImpl(private val retrofitService: MoviesRetrofitService) :
    MoviesRemoteDataSource {

    override suspend fun getMovieDetail(id: String): Flow<ApiResult<MovieDetailEntity>> {
        return try {
            val response = retrofitService.getMovieDetail(id)
            flow {
                if (response.isSuccessful) {
                    emit(
                        ApiResult.Success(
                            MovieDetailEntity.fromMovieDetailResponse(response.body()!!)
                        )
                    )
                } else {
                    emit(ApiResult.Error(HttpException(response)))
                }
            }
        } catch (e: Exception) {
            flow { emit(ApiResult.Error(e)) }
        }
    }
}