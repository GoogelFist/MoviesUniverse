package com.example.moviesuniverse.data.remote

sealed class ApiResult<T> {
    data class Success<T>(val movieDetailResponse: T) : ApiResult<T>()
    data class Error<T>(val error: Throwable) : ApiResult<T>()
}