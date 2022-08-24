package com.example.moviesuniverse.presentation.screens.tabs.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesuniverse.databinding.MovieItemBinding
import com.example.moviesuniverse.domain.models.MovieItem

class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = MovieItemBinding.bind(view)
    val root = binding.root

    fun bind(movieItem: MovieItem) {
        binding.tbMovieItem.text = movieItem.nameEn
    }
}