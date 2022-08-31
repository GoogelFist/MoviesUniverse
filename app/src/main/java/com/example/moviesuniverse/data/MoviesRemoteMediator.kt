package com.example.moviesuniverse.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.moviesuniverse.data.local.movies.MoviesDao
import com.example.moviesuniverse.data.local.movies.RemoteKeysDao
import com.example.moviesuniverse.data.local.movies.models.MovieEntity
import com.example.moviesuniverse.data.local.movies.models.RemoteKeyEntity
import com.example.moviesuniverse.data.remote.movies.MoviesRetrofitService
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val moviesRetrofitService: MoviesRetrofitService,
    private val moviesDao: MoviesDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val query: String
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> INITIAL_KEY
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = remoteKeysDao.remoteKeyByQuery(query)

                    var key: String? = null

                    remoteKey?.let { keyEntity ->
                        keyEntity.nextKey?.let { nextKey ->
                            key = nextKey
                        } ?: return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    key
                }
            }

            val movieResponse = loadKey?.let { moviesRetrofitService.getTop250MovieList(page = it) }

            var items = emptyList<MovieEntity>()
            var totalPages = 0

            movieResponse?.let { response ->
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        items = body.films.map { MovieEntity.fromMovieItemResponseFilm(it, query) }
                        totalPages = body.pagesCount
                    }
                } else {
                    return MediatorResult.Error(HttpException(response))
                }
            }

            if (loadType == LoadType.REFRESH) {
                remoteKeysDao.remoteKeyByQuery(query)
                moviesDao.deleteMoviesByQuery(query)
            }

            loadKey?.let { key ->
                val nextKey = key.toInt().plus(1)
                if (nextKey > totalPages) {
                    remoteKeysDao.insertOrReplace(RemoteKeyEntity(label = query, nextKey = null))
                } else {
                    remoteKeysDao.insertOrReplace(
                        RemoteKeyEntity(
                            label = query,
                            nextKey = nextKey.toString()
                        )
                    )
                }

                moviesDao.insertAllMovies(items)
            }

            MediatorResult.Success(
                endOfPaginationReached = items.isEmpty()
            )

        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    companion object {
        private const val INITIAL_KEY = "1"
    }
}