package com.example.moviesuniverse.data.local.staff

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesuniverse.data.local.staff.model.MovieStaffEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesStaffDao {

    @Query("SELECT * FROM movies_staff WHERE movieId LIKE :movieId")
    fun getMovieStaff(movieId: String): Flow<List<MovieStaffEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMoviesStaff(movies: List<MovieStaffEntity>)

    @Query("DELETE FROM movies_staff WHERE movieId LIKE :movieId")
    suspend fun deleteMovieStaff(movieId: String)
}