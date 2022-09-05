package com.example.moviesuniverse.data.local.movies

import androidx.paging.PagingSource
import com.example.moviesuniverse.data.local.MoviesLocalDataSource
import com.example.moviesuniverse.data.local.DaoResult
import com.example.moviesuniverse.data.local.movies.models.MovieDetailEntity
import com.example.moviesuniverse.data.local.movies.models.MovieEntity
import com.example.moviesuniverse.domain.models.MovieDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomDataSourceImpl(
    private val moviesDao: MoviesDao,
    private val moveDetailDao: MoviesDetailDao
) : MoviesLocalDataSource {

    override fun getMovieEntityPagingSource(query: String): PagingSource<Int, MovieEntity> {
        return moviesDao.pagingSource(query)
    }

    override fun getMovieDetailById(id: String): Flow<DaoResult<MovieDetail>> {
        return moveDetailDao.getMovieDetail(id).map { movieDetailEntity ->
            if (movieDetailEntity == null) {
                DaoResult.NotExist()
            } else {
                DaoResult.Exist(movieDetailEntity.toMovieDetail())
            }
        }
    }

    override suspend fun insertMovieDetail(movieDetailEntity: MovieDetailEntity) {
        moveDetailDao.insertMovieDetail(movieDetailEntity)
    }
}