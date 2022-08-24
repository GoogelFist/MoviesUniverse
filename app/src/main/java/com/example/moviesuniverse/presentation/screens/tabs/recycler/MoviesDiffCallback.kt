package com.example.moviesuniverse.presentation.screens.tabs.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.moviesuniverse.domain.models.MovieItem

class MoviesDiffCallback: DiffUtil.ItemCallback<MovieItem>() {
    override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem == newItem
    }
}