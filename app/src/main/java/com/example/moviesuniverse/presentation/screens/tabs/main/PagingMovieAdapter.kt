package com.example.moviesuniverse.presentation.screens.tabs.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviesuniverse.R
import com.example.moviesuniverse.databinding.MovieItemBinding
import com.example.moviesuniverse.domain.models.MovieItem

class PagingMovieAdapter(private val onItemClickListener: (id: String) -> Unit) :
    PagingDataAdapter<MovieItem, MovieViewHolder>(MovieComparator) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { movieItem ->
            holder.binding.tvMovieItemTitle.text = movieItem.nameRu

            holder.binding.ivPoster.load(movieItem.poster) {
                crossfade(true)
                placeholder(R.drawable.image_placeholder)
                error(R.drawable.image_error_placeholder)
                allowHardware(false)
            }

            holder.binding.root.setOnClickListener {
                onItemClickListener.invoke(movieItem.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }
}

class MovieViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)

object MovieComparator : DiffUtil.ItemCallback<MovieItem>() {
    override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem == newItem
    }
}