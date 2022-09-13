package com.example.moviesuniverse.data.remote.movies

import com.example.moviesuniverse.data.remote.movies.models.MovieDetailResponse
import com.example.moviesuniverse.data.remote.movies.models.MovieItemResponse
import com.example.moviesuniverse.data.remote.movies.models.MovieSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MoviesRetrofitService {

    @GET("top")
    suspend fun getTop250MovieList(
        @Query(TYPE) type: String = TYPE_TOP_250,
        @Query(PAGE) page: String
    ): Response<MovieItemResponse>

    @GET(" ")
    suspend fun searchMovies(
        @Query(KEYWORD) keyword: String,
        @Query(PAGE) page: String
    ): Response<MovieSearchResponse>

    @GET("{id}")
    suspend fun getMovieDetail(
        @Path(ID_PATH) id: String
    ): Response<MovieDetailResponse>


    companion object {
        private const val TYPE = "type"
        private const val KEYWORD = "keyword"
        private const val TYPE_TOP_250 = "TOP_250_BEST_FILMS"
        private const val PAGE = "page"
        private const val ID_PATH = "id"
    }
}