package com.example.moviesuniverse.data.model

sealed class ApiResult<T> {
    data class Success<T>(val movieDetailResponse: T) : ApiResult<T>()
    data class Error<T>(val error: Throwable) : ApiResult<T>()
}