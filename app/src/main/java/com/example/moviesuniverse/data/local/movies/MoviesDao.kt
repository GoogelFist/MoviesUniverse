package com.example.moviesuniverse.data.local.movies

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesuniverse.data.local.movies.models.MovieEntity

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies WHERE label LIKE :query ORDER BY addedDate ASC")
    fun pagingSource(query: String): PagingSource<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM movies WHERE label LIKE :query")
    suspend fun deleteMoviesByQuery(query: String)
}