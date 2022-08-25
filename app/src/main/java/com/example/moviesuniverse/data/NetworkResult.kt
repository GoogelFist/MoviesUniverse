package com.example.moviesuniverse.data

import com.example.moviesuniverse.data.remote.movies.models.MovieItemResponse

sealed class NetworkResult {
    data class Success(val data: List<MovieItemResponse.Film> = emptyList()) : NetworkResult()
    data class Failure(val errorMessage: String = "Default error message") : NetworkResult()
}