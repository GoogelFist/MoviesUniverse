package com.example.moviesuniverse.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.moviesuniverse.data.local.movies.DataBase
import com.example.moviesuniverse.data.local.movies.models.MovieEntity
import com.example.moviesuniverse.data.local.movies.models.RemoteKeyEntity
import com.example.moviesuniverse.data.remote.movies.MoviesRetrofitService
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val moviesRetrofitService: MoviesRetrofitService,
    private val moviesDB: DataBase,
    private val query: String
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private val movieDao = moviesDB.getMovieDao()
    private val remoteKeyDao = moviesDB.getRemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> "1"
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = moviesDB.withTransaction {
                        remoteKeyDao.remoteKeyByQuery(query)
                    }

                    var key: String? = null

                    remoteKey?.let {
                        it.nextKey?.let {
                            key = remoteKey.nextKey
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
                        items = body.films
                            .map { MovieEntity.fromMovieItemResponseFilm(it, query) }
                        totalPages = body.pagesCount
                    }
                } else {
                    return MediatorResult.Error(HttpException(response))
                }
            }

            // TODO:
            moviesDB.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.remoteKeyByQuery(query)
                    movieDao.deleteMoviesByQuery(query)
                }

                loadKey?.let {
                    if (it.toInt() + 1 > totalPages) {
                        remoteKeyDao.insertOrReplace(
                            RemoteKeyEntity(
                                label = query,
                                nextKey = null
                            )
                        )
                    } else {
                        remoteKeyDao.insertOrReplace(
                            RemoteKeyEntity(
                                label = query,
                                nextKey = (it.toInt() + 1).toString()
                            )
                        )
                    }
                }

                movieDao.insertAllMovies(items)
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
}