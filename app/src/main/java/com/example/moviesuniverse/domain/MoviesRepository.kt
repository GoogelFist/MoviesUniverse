package com.example.moviesuniverse.domain

import androidx.paging.PagingData
import com.example.moviesuniverse.domain.models.MovieItem
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getTop250Movies(): Flow<PagingData<MovieItem>>
}