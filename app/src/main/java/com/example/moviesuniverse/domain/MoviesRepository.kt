package com.example.moviesuniverse.domain

import androidx.paging.PagingData
import com.example.moviesuniverse.data.local.movies.models.MovieEntity
import com.example.moviesuniverse.data.remote.ApiResult
import com.example.moviesuniverse.domain.models.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getTop250Movies(): Flow<PagingData<MovieEntity>>

    fun searchMovies(query: String): Flow<PagingData<MovieEntity>>

    suspend fun getDetailMovie(id: String): Flow<ApiResult<MovieDetail>>
}