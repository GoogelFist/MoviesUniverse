package com.example.moviesuniverse.domain.models

data class MovieStaffDetail(
    val id: String = "",
    val nameRu: String = "",
    val nameEn: String = "",
    val posterUrl: String = "",
    val sex: Boolean = true,
    val birthDay: String = "",
    val birthPlace: String = "",
    val facts: String = ""
)