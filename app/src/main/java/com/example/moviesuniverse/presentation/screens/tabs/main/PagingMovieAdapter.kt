package com.example.moviesuniverse.presentation.screens.tabs.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.moviesuniverse.R
import com.example.moviesuniverse.databinding.MovieItemBinding
import com.example.moviesuniverse.domain.models.MovieItem

class PagingMovieAdapter(private val onItemClickListener: (id: String) -> Unit) :
    PagingDataAdapter<MovieItem, MovieViewHolder>(MovieComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)

        val viewHolder = MovieViewHolder(binding)

        binding.root.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            if (position != NO_POSITION) {
                getItem(position)?.let { item -> onItemClickListener.invoke(item.id) }
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { movieItem ->
            holder.binding.tvMovieItemTitle.text = movieItem.nameRu

            val context = holder.binding.root.context
            val cornerRadius = context
                .resources.getDimension(R.dimen.movie_item_round_corner).toInt()

            holder.binding.ivPoster.load(movieItem.poster) {
                crossfade(true)
                transformations(RoundedCornersTransformation(cornerRadius.toFloat()))
                placeholder(R.drawable.image_placeholder)
                error(R.drawable.image_error_placeholder)
            }
        }
    }

    companion object {
        private const val NO_POSITION = -1
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