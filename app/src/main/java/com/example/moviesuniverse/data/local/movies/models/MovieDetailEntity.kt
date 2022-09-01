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
    val filmLength: String = "",
    val genres: String = "",
    val nameEn: String = "",
    val nameRu: String = "",
    val posterUrl: String = "",
    val posterUrlPreview: String = "",
    val rating: String = "",
    val ratingChange: String = "",
    val ratingVoteCount: String = "",
    val year: String = "",
    val label: String = "",
    val addedDate: Long = System.currentTimeMillis()
) {

    fun toMovieDetail(): MovieDetail {
        return MovieDetail(
            id = this.filmId,
            nameRu = this.nameRu,
            nameEn = this.nameEn,
            countries = this.countries,
            genres = this.genres,
            year = this.year,
            rating = this.rating,
            poster = this.posterUrlPreview
        )
    }

    companion object {
        // TODO:
        fun fromMovieDetailResponse(movieDetailResponse: MovieDetailResponse): MovieDetailEntity {
            return MovieDetailEntity(
                filmId = movieDetailResponse.kinopoiskId.toString(),
                nameRu = movieDetailResponse.nameRu,
                nameEn = movieDetailResponse.nameEn ?: movieDetailResponse.nameRu,
                countries = movieDetailResponse.countries.toString(),
                genres = movieDetailResponse.genres.toString(),
                year = movieDetailResponse.year.toString(),
                rating = movieDetailResponse.ratingKinopoisk.toString(),
                posterUrl = movieDetailResponse.posterUrl,
                filmLength = movieDetailResponse.filmLength.toString(),
                posterUrlPreview = movieDetailResponse.posterUrlPreview,
                ratingVoteCount = movieDetailResponse.ratingKinopoiskVoteCount.toString()
            )
        }
    }
}