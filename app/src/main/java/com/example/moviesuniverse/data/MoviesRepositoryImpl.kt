package com.example.moviesuniverse.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviesuniverse.data.local.MoviesLocalDataSource
import com.example.moviesuniverse.data.local.movies.models.MovieEntity
import com.example.moviesuniverse.data.remote.MoviesRemoteDataSource
import com.example.moviesuniverse.domain.MoviesRepository
import com.example.moviesuniverse.domain.models.MovieDetail
import kotlinx.coroutines.flow.Flow
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject

class MoviesRepositoryImpl(
    private val localDataSource: MoviesLocalDataSource,
    private val remoteDataSource: MoviesRemoteDataSource
) : MoviesRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getTop250Movies(): Flow<PagingData<MovieEntity>> {
        val moviesRemoteMediator: MoviesRemoteMediator by inject(MoviesRemoteMediator::class.java) {
            parametersOf(TYPE_TOP_250)
        }

        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { localDataSource.getPagingSource(TYPE_TOP_250) },
            remoteMediator = moviesRemoteMediator
        ).flow
    }

    // TODO: will add cache and flow
    override suspend fun getDetailMovie(id: String): MovieDetail {
        val movieDetailEntity = remoteDataSource.getMovieDetail(id)
        return movieDetailEntity.toMovieDetail()
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val INITIAL_KEY = 1

        private const val TYPE_TOP_250 = "TOP_250_BEST_FILMS"
    }
}