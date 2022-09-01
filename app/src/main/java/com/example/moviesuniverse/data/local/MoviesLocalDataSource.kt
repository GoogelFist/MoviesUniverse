package com.example.moviesuniverse.data.local

import androidx.paging.PagingSource
import com.example.moviesuniverse.data.local.movies.models.MovieEntity

interface MoviesLocalDataSource {

    fun getPagingSource(query: String): PagingSource<Int, MovieEntity>
}