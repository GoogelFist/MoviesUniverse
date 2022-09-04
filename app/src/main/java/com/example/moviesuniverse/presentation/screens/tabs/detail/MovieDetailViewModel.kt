package com.example.moviesuniverse.presentation.screens.tabs.detail

import androidx.lifecycle.ViewModel
import com.example.moviesuniverse.data.model.ApiResult
import com.example.moviesuniverse.domain.usecases.LoadDetailMovieUseCase
import com.example.moviesuniverse.presentation.screens.tabs.detail.model.MovieDetailState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MovieDetailViewModel(private val loadDetailMovieUseCase: LoadDetailMovieUseCase) :
    ViewModel() {


    // TODO: will remove delay
    fun getMovieDetail(id: String): Flow<MovieDetailState> {

        return flow {

            emit(MovieDetailState.Loading)
            delay(1000)

            loadDetailMovieUseCase(id)
                .catch { e ->
                    emit(MovieDetailState.Error(e))
                }
                .collect { result ->
                    when (result) {
                        is ApiResult.Error -> emit(MovieDetailState.Error(result.error))
                        is ApiResult.Success -> emit(MovieDetailState.Success(result.movieDetailResponse))
                    }
                }
        }
    }
}