package com.example.moviesuniverse.data.local.staff.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesuniverse.data.remote.staff.model.StaffDetailResponse
import com.example.moviesuniverse.domain.models.StaffDetail
import java.text.SimpleDateFormat
import java.util.*

@Entity(
    tableName = "staff_detail"
)
data class StaffDetailEntity(
    @PrimaryKey
    val id: String = "",
    val name: String = "",
    val posterUrl: String = "",
    val birthDay: String = "",
    val birthPlace: String = "",
    val facts: String = "",
    val profession: String = ""
) {

    fun toStaffDetail(): StaffDetail {
        return StaffDetail(
            id = this.id,
            name = this.name,
            posterUrl = this.posterUrl,
            birthDay = this.birthDay,
            birthPlace = this.birthPlace,
            facts = this.facts,
            profession = this.profession
        )
    }

    companion object {
        private const val EMPTY_STRING = ""
        private const val IN_DATE_PATTERN = "yyyy-MM-dd"
        private const val OUT_DATE_PATTERN = "dd MMMM yyyy"
        private const val LANGUAGE = "ru"

        private val inDateFormat = SimpleDateFormat(IN_DATE_PATTERN, Locale.ENGLISH)
        private val outDateFormat = SimpleDateFormat(OUT_DATE_PATTERN, Locale(LANGUAGE))

        fun fromStaffDetailResponse(staffDetailResponse: StaffDetailResponse): StaffDetailEntity {
            return StaffDetailEntity(
                id = staffDetailResponse.personId,
                name = staffDetailResponse.nameRu ?: staffDetailResponse.nameEn,
                posterUrl = staffDetailResponse.posterUrl,
                birthDay = formatDate(staffDetailResponse),
                birthPlace = staffDetailResponse.birthplace,
                facts = formatFacts(staffDetailResponse.facts),
                profession = staffDetailResponse.profession
            )
        }

        private fun formatDate(staffDetailResponse: StaffDetailResponse): String {
            if (staffDetailResponse.birthday.isEmpty()) return EMPTY_STRING

            inDateFormat.parse(staffDetailResponse.birthday)?.let { date ->
                return outDateFormat.format(date)
            }
            return EMPTY_STRING
        }

        private fun formatFacts(list: List<String>): String {
            if (list.isEmpty()) return ""
            return list.joinToString(separator = "\n") { "- $it" }
        }
    }
}
