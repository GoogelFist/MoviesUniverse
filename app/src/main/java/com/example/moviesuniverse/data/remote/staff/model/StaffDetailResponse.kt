package com.example.moviesuniverse.data.remote.staff.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class StaffDetailResponse(
    @SerialName("age")
    val age: String = "",
    @SerialName("birthday")
    val birthday: String = "",
    @SerialName("birthplace")
    val birthplace: String = "",
    @SerialName("facts")
    val facts: List<String> = listOf(),
    @SerialName("nameEn")
    val nameEn: String = "",
    @SerialName("nameRu")
    val nameRu: String?,
    @SerialName("personId")
    val personId: String = "",
    @SerialName("posterUrl")
    val posterUrl: String = "",
    @SerialName("profession")
    val profession: String = "",
)