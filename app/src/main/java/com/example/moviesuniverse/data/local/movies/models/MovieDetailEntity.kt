package com.example.moviesuniverse.data.local.movies.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesuniverse.data.remote.movies.models.MovieDetailResponse
import com.example.moviesuniverse.domain.models.MovieDetail
import kotlinx.serialization.SerialName
import org.koin.core.qualifier.named


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
    val nameEn: String = "",
    val nameOriginal: String = "",
    val nameRu: String = "",
    val posterUrl: String = "",
    val posterUrlPreview: String = "",
    val ratingKinopoisk: String = "",
    val shortDescription: String = "",
    val slogan: String = "",
    val type: String = "",
    val webUrl: String = "",
    val year: String = "",

    val label: String = "",
    val addedDate: Long = System.currentTimeMillis()
) {

    fun toMovieDetail(): MovieDetail {
        return MovieDetail(
            id = this.filmId,
            countries = this.countries,
            description = this.description,
            filmLength = this.filmLength,
            genres = this.genres,
            nameEn = this.nameEn,
            nameOriginal = this.nameOriginal,
            nameRu = this.nameRu,
            posterUrl = this.posterUrl,
            posterUrlPreview = this.posterUrlPreview,
            ratingKinopoisk = this.ratingKinopoisk,
            shortDescription = this.shortDescription,
            slogan = this.slogan,
            type = this.type,
            webUrl = this.webUrl,
            year = this.year
        )
    }

    companion object {
        // TODO: will think about mapping
        fun fromMovieDetailResponse(movieDetailResponse: MovieDetailResponse): MovieDetailEntity {
            return MovieDetailEntity(
                filmId = movieDetailResponse.kinopoiskId.toString(),
                countries = movieDetailResponse.countries.toString(),
                description = movieDetailResponse.description,
                filmLength = movieDetailResponse.filmLength.toString(),
                genres = movieDetailResponse.genres.toString(),
                nameEn = movieDetailResponse.nameEn,
                nameOriginal = movieDetailResponse.nameOriginal,
                nameRu = movieDetailResponse.nameRu,
                posterUrl = movieDetailResponse.posterUrl,
                posterUrlPreview = movieDetailResponse.posterUrlPreview,
                ratingKinopoisk = movieDetailResponse.ratingKinopoisk.toString(),
                shortDescription = movieDetailResponse.shortDescription,
                slogan = movieDetailResponse.slogan,
                type = movieDetailResponse.type,
                webUrl = movieDetailResponse.webUrl,
                year = movieDetailResponse.year.toString(),
            )
        }
    }
}