package com.example.moviesuniverse.data.remote.movies

import com.example.moviesuniverse.data.remote.movies.models.MovieItemResponse
import retrofit2.Response
import retrofit2.http.*


interface MoviesRetrofitService {

    @GET("top")
    suspend fun getTop250MovieList(
        @Query(TYPE) type: String = TYPE_TOP_250,
        @Query(PAGE) page: String
    ): Response<MovieItemResponse>


    companion object {
        private const val TYPE = "type"
        private const val TYPE_TOP_250 = "TOP_250_BEST_FILMS"
        private const val PAGE = "page"

    }
}