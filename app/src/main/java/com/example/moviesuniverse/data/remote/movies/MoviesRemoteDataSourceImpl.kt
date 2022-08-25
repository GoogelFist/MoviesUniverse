package com.example.moviesuniverse.data.remote.movies

import com.example.moviesuniverse.data.NetworkResult
import com.example.moviesuniverse.data.remote.MoviesRemoteDataSource

class MoviesRemoteDataSourceImpl(private val moviesRetrofitService: MoviesRetrofitService) :
    MoviesRemoteDataSource {

    // TODO:
    override suspend fun loadMovies(): NetworkResult {
        val response = moviesRetrofitService.getTop250MovieList(page = "1")
        return if (response.isSuccessful) {
            NetworkResult.Success(response.body()?.films ?: emptyList())
        } else {
            NetworkResult.Failure(response.message())
        }
    }
}

