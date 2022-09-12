package com.example.moviesuniverse.data.remote.movies.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieSearchResponse(
    @SerialName("items")
    val items: List<Item> = listOf(),
    @SerialName("total")
    val total: Int = 0,
    @SerialName("totalPages")
    val totalPages: Int = 0
) {
    @Serializable
    data class Item(
        @SerialName("kinopoiskId")
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