package com.example.moviesuniverse.data.remote

import com.example.moviesuniverse.data.local.movies.models.MovieDetailEntity

interface MoviesRemoteDataSource {

    suspend fun getMovieDetail(id: String): MovieDetailEntity
}