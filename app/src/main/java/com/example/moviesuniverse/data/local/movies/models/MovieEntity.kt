package com.example.moviesuniverse.data.local.movies.models

import androidx.room.Entity
import androidx.room.Index
import com.example.moviesuniverse.data.remote.movies.models.MovieItemResponse
import com.example.moviesuniverse.data.remote.movies.models.MovieSearchResponse
import com.example.moviesuniverse.domain.models.MovieItem


@Entity(
    primaryKeys = (["filmId", "label"]),
    tableName = "movies",
    indices = [
        Index(value = ["label"], unique = false),
        Index(value = ["addedDate"], unique = false)
    ]
)
data class MovieEntity(
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

        fun fromMovieSearchResponseItem(
            item: MovieSearchResponse.Item,
            query: String
        ): MovieEntity {
            return MovieEntity(
                filmId = item.filmId.toString(),
                nameRu = item.nameRu,
                label = query,
                posterUrlPreview = item.posterUrlPreview
            )
        }
    }
}