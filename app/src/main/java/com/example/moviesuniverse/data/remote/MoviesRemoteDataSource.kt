package com.example.moviesuniverse.data.remote

import com.example.moviesuniverse.data.local.movies.models.MovieDetailEntity
import kotlinx.coroutines.flow.Flow

interface MoviesRemoteDataSource {

    suspend fun getMovieDetail(id: String): Flow<ApiResult<MovieDetailEntity>>
}