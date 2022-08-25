package com.example.moviesuniverse.data.remote.movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesuniverse.data.remote.movies.models.toMovieItem
import com.example.moviesuniverse.domain.models.MovieItem

class MoviePagingSource(private val moviesRetrofitService: MoviesRetrofitService) :
    PagingSource<Int, MovieItem>() {

    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        return try {
            val position = params.key ?: START_POSITION
            val response = moviesRetrofitService.getTop250MovieList(page = position.toString())
            LoadResult.Page(
                data = response.body()!!.films.map { it.toMovieItem() },
                prevKey = getPrevKey(position),
                nextKey = position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun getPrevKey(position: Int): Int? {
        return if (position == START_POSITION) {
            null
        } else {
            position - 1
        }
    }

    companion object {
        private const val START_POSITION = 1
    }
}