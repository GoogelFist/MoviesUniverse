package com.example.moviesuniverse.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviesuniverse.data.local.MoviesLocalDataSource
import com.example.moviesuniverse.data.local.movies.models.MovieEntity
import com.example.moviesuniverse.data.model.ApiResult
import com.example.moviesuniverse.data.remote.MoviesRemoteDataSource
import com.example.moviesuniverse.domain.MoviesRepository
import com.example.moviesuniverse.domain.models.MovieDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
    override suspend fun getDetailMovie(id: String): Flow<ApiResult<MovieDetail>> {
        return remoteDataSource.getMovieDetail(id).map { response ->
            when (response) {
                is ApiResult.Error -> ApiResult.Error(response.error)
                is ApiResult.Success -> {
                    ApiResult.Success(
                        response.movieDetailResponse.toMovieDetail()
                    )
                }
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 20

        private const val TYPE_TOP_250 = "TOP_250_BEST_FILMS"
    }
}