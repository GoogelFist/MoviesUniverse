package com.example.moviesuniverse.data.remote.movies.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailResponse(
    @SerialName("countries")
    val countries: List<Country> = listOf(),
    @SerialName("description")
    val description: String = "",
    @SerialName("filmLength")
    val filmLength: Int = 0,
    @SerialName("genres")
    val genres: List<Genre> = listOf(),
    @SerialName("kinopoiskId")
    val kinopoiskId: Int = 0,
    @SerialName("nameRu")
    val nameRu: String = "",
    @SerialName("posterUrl")
    val posterUrl: String = "",
    @SerialName("ratingKinopoisk")
    val ratingKinopoisk: Double = 0.0,
    @SerialName("slogan")
    val slogan: String = "",
    @SerialName("year")
    val year: Int = 0
) {
    @Serializable
    data class Country(
        @SerialName("country")
        val country: String = ""
    )

    @Serializable
    data class Genre(
        @SerialName("genre")
        val genre: String = ""
    )
}