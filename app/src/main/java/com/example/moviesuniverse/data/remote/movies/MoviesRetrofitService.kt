package com.example.moviesuniverse.data.remote.movies

import com.example.moviesuniverse.data.remote.movies.models.MovieItemDTO
import retrofit2.Response
import retrofit2.http.*


interface MoviesRetrofitService {

    @GET("top")
    suspend fun getMovieList(
        @Query(TYPE) type: String,
        @Query(PAGE) page: String
    ): Response<MovieItemDTO>


    companion object {
        private const val TYPE = "type"
        private const val PAGE = "page"
    }
}