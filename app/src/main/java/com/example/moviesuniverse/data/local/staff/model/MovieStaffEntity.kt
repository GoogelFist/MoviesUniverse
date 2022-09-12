package com.example.moviesuniverse.data.local.staff.model

import androidx.room.Entity
import androidx.room.Index
import com.example.moviesuniverse.data.remote.staff.model.MovieStaffResponse
import com.example.moviesuniverse.domain.models.MovieStaffItem

@Entity(
    primaryKeys = (["movieStaffId", "movieId"]),
    tableName = "movies_staff",
    indices = [
        Index(value = ["movieId"], unique = false),
    ]
)

data class MovieStaffEntity(
    val movieStaffId: String = "",
    val movieId: String = "",
    val name: String = "",
    val posterUrl: String = "",
    val description: String = "",
    val professionText: String = ""
) {

    fun toMovieStaffItem(): MovieStaffItem {
        return MovieStaffItem(
            id = this.movieStaffId,
            name = this.name,
            description = this.description,
            posterUrl = this.posterUrl,
            professionText = professionText
        )
    }

    companion object {

        fun fromMovieStaffResponse(
            movieStaffResponse: MovieStaffResponse,
            movieId: String
        ): MovieStaffEntity {
            return MovieStaffEntity(
                movieStaffId = movieStaffResponse.staffId,
                movieId = movieId,
                name = movieStaffResponse.nameRu ?: movieStaffResponse.nameEn,
                description = movieStaffResponse.description,
                posterUrl = movieStaffResponse.posterUrl,
                professionText = movieStaffResponse.professionText
            )
        }
    }
}