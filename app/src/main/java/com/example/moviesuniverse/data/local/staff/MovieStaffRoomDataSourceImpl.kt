package com.example.moviesuniverse.data.local.staff

import com.example.moviesuniverse.data.local.DaoResult
import com.example.moviesuniverse.data.local.MovieStaffLocalDataSource
import com.example.moviesuniverse.data.local.staff.model.MovieStaffEntity
import com.example.moviesuniverse.data.local.staff.model.StaffDetailEntity
import com.example.moviesuniverse.domain.models.MovieStaffItem
import com.example.moviesuniverse.domain.models.StaffDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class MovieStaffRoomDataSourceImpl(
    private val movieStaffDao: MoviesStaffDao,
    private val staffDetailDao: StaffDetailDao
) : MovieStaffLocalDataSource {

    override fun getMovieStaff(movieId: String): Flow<DaoResult<List<MovieStaffItem>>> {
        return movieStaffDao.getMovieStaff(movieId).map { movieStaff ->
            if (movieStaff.isEmpty()) {
                DaoResult.NotExist()
            } else {
                DaoResult.Exist(movieStaff.map { it.toMovieStaffItem() })
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun insertAllMoviesStaff(movieStaff: List<MovieStaffEntity>) {
        movieStaffDao.insertAllMoviesStaff(movieStaff)
    }

    override suspend fun deleteMovieStaff(movieId: String) {
        movieStaffDao.deleteMovieStaff(movieId)
    }

    override fun getStaffDetail(staffId: String): Flow<DaoResult<StaffDetail>> {
        return staffDetailDao.getStaffDetail(staffId).map { staffDetail ->
            if (staffDetail == null) {
                DaoResult.NotExist()
            } else {
                DaoResult.Exist(staffDetail.toStaffDetail())
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun insertStaffDetail(staffDetail: StaffDetailEntity) {
        staffDetailDao.insertStaffDetail(staffDetail)
    }

    override suspend fun deleteStaffDetail(staffId: String) {
        staffDetailDao.deleteStaffDetail(staffId)
    }
}