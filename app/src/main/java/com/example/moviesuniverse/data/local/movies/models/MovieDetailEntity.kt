package com.example.moviesuniverse.data.local.movies.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesuniverse.data.remote.movies.models.MovieDetailResponse
import com.example.moviesuniverse.domain.models.MovieDetail


@Entity(
    tableName = "movies_detail"
)
data class MovieDetailEntity(
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

        fun fromMovieDetailResponse(movieDetailResponse: MovieDetailResponse): MovieDetailEntity {
            return MovieDetailEntity(
                filmId = movieDetailResponse.kinopoiskId.toString(),
                countries = formatCounties(movieDetailResponse),
                description = formatDescription(movieDetailResponse),
                filmLength = formatLength(movieDetailResponse),
                genres = formatGenres(movieDetailResponse),
                nameRu = movieDetailResponse.nameRu,
                posterUrl = movieDetailResponse.posterUrl,
                ratingKinopoisk = movieDetailResponse.ratingKinopoisk.toString(),
                slogan = movieDetailResponse.slogan,
                year = formatYear(movieDetailResponse),
            )
        }

        private fun formatYear(movieDetailResponse: MovieDetailResponse) =
            "${movieDetailResponse.year } $YEAR_FIELD_POSTFIX"

        private fun formatLength(movieDetailResponse: MovieDetailResponse) =
            "${movieDetailResponse.filmLength } $LENGTH_FIELD_POSTFIX"

        private fun formatDescription(movieDetailResponse: MovieDetailResponse) =
            "\t\t${movieDetailResponse.description}"

        private fun formatGenres(movieDetailResponse: MovieDetailResponse) =
            movieDetailResponse.genres.joinToString(separator = GENRES_SEPARATOR) { it.genre }

        private fun formatCounties(movieDetailResponse: MovieDetailResponse) =
            movieDetailResponse.countries.joinToString(separator = COUNTRIES_SEPARATOR) { it.country }

        private const val LENGTH_FIELD_POSTFIX = "мин."
        private const val YEAR_FIELD_POSTFIX = "г."
        private const val GENRES_SEPARATOR = ", "
        private const val COUNTRIES_SEPARATOR = ", "
    }
}