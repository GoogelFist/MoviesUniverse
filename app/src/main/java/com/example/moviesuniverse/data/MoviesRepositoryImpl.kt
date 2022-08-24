package com.example.moviesuniverse.data

import android.util.Log
import com.example.moviesuniverse.data.remote.MoviesRemoteDataSource
import com.example.moviesuniverse.data.remote.movies.models.MovieItemDTO
import com.example.moviesuniverse.domain.MoviesRepository

class MoviesRepositoryImpl(private val moviesRemoteDataSource: MoviesRemoteDataSource): MoviesRepository {
    lateinit var data: List<MovieItemDTO.Film>

    override suspend fun loadMovies() {
        val networkResult = moviesRemoteDataSource.loadMovies()
        when (networkResult) {
            is NetworkResult.Success -> {
                data = networkResult.data
            }
            is NetworkResult.Failure -> {
                Log.e("MoviesRepositoryImpl", networkResult.errorMessage)
            }
        }
    }
}