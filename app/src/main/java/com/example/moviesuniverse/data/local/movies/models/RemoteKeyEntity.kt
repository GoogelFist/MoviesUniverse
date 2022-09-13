package com.example.moviesuniverse.data.local.movies.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
class RemoteKeyEntity(
    @PrimaryKey
    val label: String = "",
    val nextKey: String? = null
)