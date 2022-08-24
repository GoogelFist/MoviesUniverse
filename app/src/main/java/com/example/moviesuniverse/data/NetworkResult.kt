package com.example.moviesuniverse.data

import com.example.moviesuniverse.data.remote.movies.models.MovieItemDTO

sealed class NetworkResult {
    data class Success(val data: List<MovieItemDTO.Film> = emptyList()) : NetworkResult()
    data class Failure(val errorMessage: String = "Default error message") : NetworkResult()
}