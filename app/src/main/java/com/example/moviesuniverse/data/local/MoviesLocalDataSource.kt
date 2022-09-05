package com.example.moviesuniverse.data.local

import androidx.paging.PagingSource
import com.example.moviesuniverse.data.local.movies.models.MovieDetailEntity
import com.example.moviesuniverse.data.local.movies.models.MovieEntity
import com.example.moviesuniverse.domain.models.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MoviesLocalDataSource {

    fun getMovieEntityPagingSource(query: String): PagingSource<Int, MovieEntity>

    fun getMovieDetailById(id: String): Flow<DaoResult<MovieDetail>>

    suspend fun insertMovieDetail(movieDetailEntity: MovieDetailEntity)
}