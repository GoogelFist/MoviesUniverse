package com.example.moviesuniverse.data.local.movies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesuniverse.data.local.movies.models.MovieDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDetailDao {
    @Query("SELECT * FROM movies_detail WHERE filmId LIKE :id")
    fun getMovieDetail(id: String): Flow<MovieDetailEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movie: MovieDetailEntity)
}