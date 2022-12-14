package com.example.moviesuniverse.data.local.movies.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesuniverse.data.remote.movies.models.MovieDetailResponse
import com.example.moviesuniverse.domain.models.MovieDetail


@Entity(
    tableName = "movies_detail"
)
class MovieDetailEntity(
    @PrimaryKey
    val filmId: String = "",
    val countries: String = "",
    val description: String = "",
    val filmLength: String = "",
    val genres: String = "",
    val nameRu: String = "",
    val posterUrl: String = "",
    val ratingKinopoisk: String = "",
    val slogan: String = "",
    val year: String = "",

    val addedDate: Long = System.currentTimeMillis()
) {

    fun toMovieDetail(): MovieDetail {
        return MovieDetail(
            id = this.filmId,
            countries = this.countries,
            description = this.description,
            filmLength = this.filmLength,
            genres = this.genres,
            nameRu = this.nameRu,
            posterUrl = this.posterUrl,
            ratingKinopoisk = this.ratingKinopoisk,
            slogan = this.slogan,
            year = this.year
        )
    }

    companion object {

        fun fromMovieDetailResponse(response: MovieDetailResponse): MovieDetailEntity {
            return MovieDetailEntity(
                filmId = response.kinopoiskId,
                countries = formatCounties(response),
                description = response.description,
                filmLength = formatLength(response),
                genres = formatGenres(response),
                nameRu = response.nameRu ?: response.nameEn ?: response.nameOriginal,
                posterUrl = response.posterUrl,
                ratingKinopoisk = response.ratingKinopoisk,
                slogan = response.slogan,
                year = formatYear(response),
            )
        }

        private fun formatYear(movieDetailResponse: MovieDetailResponse) =
            "${movieDetailResponse.year } $YEAR_FIELD_POSTFIX"

        private fun formatLength(movieDetailResponse: MovieDetailResponse): String {
            if (movieDetailResponse.filmLength.isEmpty()) return ""
            return "${movieDetailResponse.filmLength } $LENGTH_FIELD_POSTFIX"
        }

        private fun formatGenres(movieDetailResponse: MovieDetailResponse) =
            movieDetailResponse.genres.joinToString(separator = GENRES_SEPARATOR) { it.genre }

        private fun formatCounties(movieDetailResponse: MovieDetailResponse) =
            movieDetailResponse.countries.joinToString(separator = COUNTRIES_SEPARATOR) { it.country }

        private const val LENGTH_FIELD_POSTFIX = "??????."
        private const val YEAR_FIELD_POSTFIX = "??."
        private const val GENRES_SEPARATOR = ", "
        private const val COUNTRIES_SEPARATOR = ", "
    }
}