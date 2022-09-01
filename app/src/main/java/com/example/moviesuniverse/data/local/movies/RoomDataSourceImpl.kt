package com.example.moviesuniverse.data.local.movies

import androidx.paging.PagingSource
import com.example.moviesuniverse.data.local.MoviesLocalDataSource
import com.example.moviesuniverse.data.local.movies.models.MovieEntity

class RoomDataSourceImpl(private val moviesDao: MoviesDao): MoviesLocalDataSource {
    override fun getPagingSource(query: String): PagingSource<Int, MovieEntity> {
        return moviesDao.pagingSource(query)
    }
}