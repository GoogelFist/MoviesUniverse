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
    val nameRu: String = "",
    val posterUrlPreview: String = "",
    val label: String = "",
    val addedDate: Long = System.currentTimeMillis()
) {

    fun toMovieItem(): MovieItem {
        return MovieItem(
            id = this.filmId,
            nameRu = this.nameRu,
            poster = this.posterUrlPreview
        )
    }

    companion object {
        fun fromMovieItemResponseFilm(film: MovieItemResponse.Film, query: String): MovieEntity {
            return MovieEntity(
                filmId = film.filmId.toString(),
                nameRu = film.nameRu,
                label = query,
                posterUrlPreview = film.posterUrlPreview
            )
        }
    }
}