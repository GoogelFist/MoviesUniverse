package com.example.moviesuniverse.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviesuniverse.data.local.movies.MoviesDao
import com.example.moviesuniverse.data.local.movies.models.MovieEntity
import com.example.moviesuniverse.di.data.TYPE_TOP_250
import com.example.moviesuniverse.domain.MoviesRepository
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl(
    private val moviesRemoteMediator: MoviesRemoteMediator,
    private val moviesDao: MoviesDao
) : MoviesRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getTop250Movies(): Flow<PagingData<MovieEntity>> {

        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            initialKey = INITIAL_KEY,
            pagingSourceFactory = { moviesDao.pagingSource(TYPE_TOP_250) },
            remoteMediator = moviesRemoteMediator
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val INITIAL_KEY = 1
    }
}