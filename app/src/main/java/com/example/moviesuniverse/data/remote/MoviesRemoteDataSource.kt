package com.example.moviesuniverse.data.remote

import com.example.moviesuniverse.data.NetworkResult

interface MoviesRemoteDataSource {

    suspend fun loadMovies(): NetworkResult
}