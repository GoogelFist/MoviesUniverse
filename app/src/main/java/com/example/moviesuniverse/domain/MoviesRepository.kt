package com.example.moviesuniverse.domain

import androidx.paging.PagingData
import com.example.moviesuniverse.data.local.movies.models.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getTop250Movies(): Flow<PagingData<MovieEntity>>
}