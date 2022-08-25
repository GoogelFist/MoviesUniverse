package com.example.moviesuniverse.data

import android.util.Log
import com.example.moviesuniverse.data.remote.MoviesRemoteDataSource
import com.example.moviesuniverse.data.remote.movies.models.MovieItemDTO
import com.example.moviesuniverse.domain.MoviesRepository
import com.example.moviesuniverse.domain.models.MovieItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(private val moviesRemoteDataSource: MoviesRemoteDataSource) :
    MoviesRepository {

    override suspend fun loadMovies(): List<MovieItem> {
       return withContext(Dispatchers.IO) {
           val networkResult = moviesRemoteDataSource.loadMovies()
           return@withContext when (networkResult) {
               is NetworkResult.Success -> {
                   networkResult.data.map { it.toMovieItem() }
               }
               is NetworkResult.Failure -> {
                   Log.e("MoviesRepositoryImpl", networkResult.errorMessage)
                   emptyList<MovieItem>()
               }
           }
        }
    }

    private fun MovieItemDTO.Film.toMovieItem(): MovieItem {
        return MovieItem(
            id = this.filmId.toString(),
            nameRu = this.nameRu,
            nameEn = this.nameEn ?: "",
            countries = this.countries.toString(),
            genres = this.genres.toString(),
            year = this.year,
            rating = this.rating,
            poster = this.posterUrl
        )
    }
}