package com.example.moviesuniverse.presentation.screens.tabs.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesuniverse.data.remote.movies.models.MovieItemResponse
import com.example.moviesuniverse.databinding.MovieItemBinding

class PagingMovieAdapter: PagingDataAdapter<MovieItemResponse.Film, PagingMovieAdapter.MovieViewHolder>(MovieComparator) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)!!
        holder.view.tbMovieItem.text = movie.nameEn
//        Glide.with(holder.itemView.context).load("https://image.tmdb.org/t/p/w300"+movie.poster_path).into(holder.view.imageview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    class MovieViewHolder(val view: MovieItemBinding): RecyclerView.ViewHolder(view.root) {

    }

    object MovieComparator: DiffUtil.ItemCallback<MovieItemResponse.Film>() {
        override fun areItemsTheSame(
            oldItem: MovieItemResponse.Film,
            newItem: MovieItemResponse.Film
        ): Boolean {
            return oldItem.filmId == newItem.filmId
        }

        override fun areContentsTheSame(
            oldItem: MovieItemResponse.Film,
            newItem: MovieItemResponse.Film
        ): Boolean {
            return oldItem == newItem
        }
    }
}