package com.example.moviesuniverse.data.remote.movies

import com.example.moviesuniverse.data.NetworkResult
import com.example.moviesuniverse.data.remote.MoviesRemoteDataSource

class MoviesRemoteDataSourceImpl(private val moviesRetrofitService: MoviesRetrofitService) :
    MoviesRemoteDataSource {

    // TODO:
    override suspend fun loadMovies(): NetworkResult {
        val response = moviesRetrofitService.getMovieList(TYPE_TOP_250, "1")
        return if (response.isSuccessful) {
            response.body()?.let {
                NetworkResult.Success(it.films)
            }
            NetworkResult.Success()
        } else {
            NetworkResult.Failure(response.message())
        }
    }
}

private const val TYPE_TOP_250 = "TOP_250_BEST_FILMS"