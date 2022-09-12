package com.example.moviesuniverse.data.remote.staff.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MovieStaffResponse(
    @SerialName("description")
    val description: String = "",
    @SerialName("nameEn")
    val nameEn: String = "",
    @SerialName("nameRu")
    val nameRu: String?,
    @SerialName("posterUrl")
    val posterUrl: String = "",
    @SerialName("professionKey")
    val professionKey: String = "",
    @SerialName("professionText")
    val professionText: String = "",
    @SerialName("staffId")
    val staffId: String = ""
)