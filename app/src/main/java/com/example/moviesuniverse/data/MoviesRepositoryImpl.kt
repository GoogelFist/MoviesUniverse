package com.example.moviesuniverse.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.moviesuniverse.data.remote.MoviesRemoteDataSource
import com.example.moviesuniverse.data.remote.movies.MoviePagingSource
import com.example.moviesuniverse.data.remote.movies.models.MovieItemResponse
import com.example.moviesuniverse.domain.MoviesRepository
import com.example.moviesuniverse.domain.models.MovieItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject

class MoviesRepositoryImpl(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val moviePagingSource: MoviePagingSource
) :
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

//    private val moviePagingSource by inject<MoviePagingSource>(  )

    override fun getAllMovies(): LiveData<PagingData<MovieItemResponse.Film>> {

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = { moviePagingSource },
            initialKey = 1
        ).liveData
    }


    private fun MovieItemResponse.Film.toMovieItem(): MovieItem {
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