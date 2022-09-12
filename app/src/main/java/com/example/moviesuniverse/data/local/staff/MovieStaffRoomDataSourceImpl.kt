package com.example.moviesuniverse.data.local.staff

import com.example.moviesuniverse.data.local.DaoResult
import com.example.moviesuniverse.data.local.MovieStaffLocalDataSource
import com.example.moviesuniverse.data.local.staff.model.MovieStaffEntity
import com.example.moviesuniverse.domain.models.MovieStaffItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieStaffRoomDataSourceImpl(private val movieStaffDao: MoviesStaffDao) :
    MovieStaffLocalDataSource {

    override suspend fun getMovieStaff(movieId: String): Flow<DaoResult<List<MovieStaffItem>>> {
        return movieStaffDao.getMovieStaff(movieId).map { movieStaff ->
            if (movieStaff.isEmpty()) {
                DaoResult.NotExist()
            } else {
                DaoResult.Exist(movieStaff.map { it.toMovieStaffItem() })
            }
        }
    }

    override suspend fun insertAllMoviesStaff(movieStaff: List<MovieStaffEntity>) {
        movieStaffDao.insertAllMoviesStaff(movieStaff)
    }

    override suspend fun deleteMovieStaff(movieId: String) {
        movieStaffDao.deleteMovieStaff(movieId)
    }
}