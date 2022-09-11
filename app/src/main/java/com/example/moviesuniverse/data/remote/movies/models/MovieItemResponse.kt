package com.example.moviesuniverse.data.remote.movies.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
        @SerialName("nameRu")
        val nameRu: String?,
        @SerialName("nameEn")
        val nameEn: String?,
        @SerialName("nameOriginal")
        val nameOriginal: String = "",
        @SerialName("posterUrlPreview")
        val posterUrlPreview: String = ""
    )
}