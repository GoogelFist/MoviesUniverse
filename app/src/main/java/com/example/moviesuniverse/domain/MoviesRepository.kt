package com.example.moviesuniverse.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.moviesuniverse.domain.models.MovieItem

interface MoviesRepository {

    fun getTop250Movies(): LiveData<PagingData<MovieItem>>
}