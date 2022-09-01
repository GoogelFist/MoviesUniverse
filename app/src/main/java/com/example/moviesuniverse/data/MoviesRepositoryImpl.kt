package com.example.moviesuniverse.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviesuniverse.data.local.movies.MoviesDao
import com.example.moviesuniverse.data.local.movies.models.MovieEntity
import com.example.moviesuniverse.domain.MoviesRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject

class MoviesRepositoryImpl(
    private val moviesDao: MoviesDao
) : MoviesRepository {

    private val moviesRemoteMediator: MoviesRemoteMediator by inject(MoviesRemoteMediator::class.java) {
        parametersOf(TYPE_TOP_250)
    }

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

        private const val TYPE_TOP_250 = "TOP_250_BEST_FILMS"
    }
}