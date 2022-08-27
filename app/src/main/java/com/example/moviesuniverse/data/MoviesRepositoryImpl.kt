package com.example.moviesuniverse.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviesuniverse.data.remote.movies.MoviePagingSource
import com.example.moviesuniverse.domain.MoviesRepository
import com.example.moviesuniverse.domain.models.MovieItem
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl(
    private val moviePagingSource: MoviePagingSource
) : MoviesRepository {

    override fun getTop250Movies(): Flow<PagingData<MovieItem>> {

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = INITIAL_LOAD_SIZE
            ),
            pagingSourceFactory = { moviePagingSource },
            initialKey = INITIAL_KEY
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val INITIAL_LOAD_SIZE = 2
        private const val INITIAL_KEY = 1
    }
}