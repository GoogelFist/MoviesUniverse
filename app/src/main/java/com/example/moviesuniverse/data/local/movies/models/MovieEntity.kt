package com.example.moviesuniverse.data.local.movies.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.moviesuniverse.data.remote.movies.models.MovieItemResponse
import com.example.moviesuniverse.domain.models.MovieItem


@Entity(
    tableName = "movies",
    indices = [
        Index(value = ["label"], unique = false),
        Index(value = ["addedDate"], unique = false)
    ]
)
data class MovieEntity(
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

    fun toMovieItem(): MovieItem {
        return MovieItem(
            id = this.filmId,
            nameRu = this.nameRu,
            nameEn = this.nameEn,
            countries = this.countries,
            genres = this.genres,
            year = this.year,
            rating = this.rating,
            poster = this.posterUrl
        )
    }

    companion object {
        // TODO:
        fun fromMovieItemResponseFilm(film: MovieItemResponse.Film, query: String): MovieEntity {
            return MovieEntity(
                filmId = film.filmId.toString(),
                nameRu = film.nameRu,
                nameEn = film.nameEn ?: film.nameRu,
                countries = film.countries.toString(),
                genres = film.genres.toString(),
                label = query,
                year = film.year,
                rating = film.rating,
                posterUrl = film.posterUrl,
                filmLength = film.filmLength,
                posterUrlPreview = film.posterUrlPreview,
                ratingChange = film.ratingChange.toString(),
                ratingVoteCount = film.ratingVoteCount.toString()
            )
        }
    }
}