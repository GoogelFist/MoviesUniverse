package com.example.moviesuniverse.data.local.movies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesuniverse.data.local.movies.models.MovieEntity


@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies")
    suspend fun loadMovies(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movieEntities: List<MovieEntity>)
}