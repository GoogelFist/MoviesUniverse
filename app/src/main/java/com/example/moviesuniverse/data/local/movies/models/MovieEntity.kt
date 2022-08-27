package com.example.moviesuniverse.data.local.movies.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movies")
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
    val year: String = ""
)