package com.example.moviesuniverse.data.local

import com.example.moviesuniverse.data.local.staff.model.MovieStaffEntity
import com.example.moviesuniverse.domain.models.MovieStaffItem
import kotlinx.coroutines.flow.Flow

interface MovieStaffLocalDataSource {

    suspend fun getMovieStaff(movieId: String): Flow<DaoResult<List<MovieStaffItem>>>

    suspend fun insertAllMoviesStaff(movieStaff: List<MovieStaffEntity>)

    suspend fun deleteMovieStaff(movieId: String)
}