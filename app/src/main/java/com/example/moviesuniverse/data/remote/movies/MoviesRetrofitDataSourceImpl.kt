package com.example.moviesuniverse.data.remote.movies

import com.example.moviesuniverse.data.local.movies.models.MovieDetailEntity
import com.example.moviesuniverse.data.remote.MoviesRemoteDataSource

class MoviesRetrofitDataSourceImpl(private val retrofitService: MoviesRetrofitService): MoviesRemoteDataSource {

    // TODO: replace with flow
    override suspend fun getMovieDetail(id: String): MovieDetailEntity {
        val response = retrofitService.getMovieDetail(id)
        if (response.isSuccessful) {
            return MovieDetailEntity.fromMovieDetailResponse(response.body()!!)
        }
        return MovieDetailEntity()
    }
}