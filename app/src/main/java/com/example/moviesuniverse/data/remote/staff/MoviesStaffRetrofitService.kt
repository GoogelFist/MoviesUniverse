package com.example.moviesuniverse.data.remote.staff

import com.example.moviesuniverse.data.remote.staff.model.MovieStaffResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface MoviesStaffRetrofitService {

    @GET("/api/v1/staff")
    suspend fun getMovieStaff(
        @Query(FILM_ID) movieId: String
    ): Response<List<MovieStaffResponse>>

    companion object {
        private const val FILM_ID = "filmId"
    }
}