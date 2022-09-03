package com.example.moviesuniverse.data.remote.movies.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO: change this model 
@Serializable
data class MovieItemResponse(
    @SerialName("films")
    val films: List<Film> = listOf(),
    @SerialName("pagesCount")
    val pagesCount: Int = 0
) {
    @Serializable
    data class Film(
        @SerialName("filmId")
        val filmId: Int = 0,
        @SerialName("nameEn")
        val nameEn: String = "",
        @SerialName("nameRu")
        val nameRu: String = "",
        @SerialName("posterUrlPreview")
        val posterUrlPreview: String = ""
    )
}