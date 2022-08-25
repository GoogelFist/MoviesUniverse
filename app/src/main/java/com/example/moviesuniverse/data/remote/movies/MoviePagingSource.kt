package com.example.moviesuniverse.data.remote.movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesuniverse.data.remote.movies.models.MovieItemResponse

class MoviePagingSource(private val moviesRetrofitService: MoviesRetrofitService) :
    PagingSource<Int, MovieItemResponse.Film>() {

    override fun getRefreshKey(state: PagingState<Int, MovieItemResponse.Film>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItemResponse.Film> {
        return try {
            val position = params.key ?: 1
            val response = moviesRetrofitService.getTop250MovieList(page = position.toString())
            LoadResult.Page(
                data = response.body()?.films!!,
                prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}