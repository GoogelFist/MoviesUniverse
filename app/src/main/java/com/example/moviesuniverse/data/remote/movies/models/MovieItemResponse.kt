package com.example.moviesuniverse.data.remote.movies.models


import com.example.moviesuniverse.data.local.movies.models.MovieEntity
import com.example.moviesuniverse.domain.models.MovieItem
import kotlinx.serialization.Contextual
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
        @SerialName("countries")
        val countries: List<Country> = listOf(),
        @SerialName("filmId")
        val filmId: Int = 0,
        @SerialName("filmLength")
        val filmLength: String = "",
        @SerialName("genres")
        val genres: List<Genre> = listOf(),
        @SerialName("nameEn")
        val nameEn: String? = null,
        @SerialName("nameRu")
        val nameRu: String = "",
        @SerialName("posterUrl")
        val posterUrl: String = "",
        @SerialName("posterUrlPreview")
        val posterUrlPreview: String = "",
        @SerialName("rating")
        val rating: String = "",
        @SerialName("ratingChange")
        @Contextual
        val ratingChange: Any? = null,
        @SerialName("ratingVoteCount")
        val ratingVoteCount: Int = 0,
        @SerialName("year")
        val year: String = ""
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
}

fun MovieItemResponse.Film.toMovieItem(): MovieItem {
    return MovieItem(
        id = this.filmId.toString(),
        nameRu = this.nameRu,
        nameEn = this.nameEn ?: nameRu,
        countries = this.countries.toString(),
        genres = this.genres.toString(),
        year = this.year,
        rating = this.rating,
        poster = this.posterUrl
    )
}

fun MovieItemResponse.Film.toMovieEntity(): MovieEntity {
    return MovieEntity(
        filmId = this.filmId.toString(),
        nameRu = this.nameRu,
        nameEn = this.nameEn ?: nameRu,
        countries = this.countries.toString(),
        genres = this.genres.toString(),
        year = this.year,
        rating = this.rating,
        posterUrl = this.posterUrl,
        filmLength = this.filmLength,
        posterUrlPreview = this.posterUrlPreview,
        ratingChange = this.ratingChange.toString(),
        ratingVoteCount = this.ratingVoteCount.toString()
    )
}