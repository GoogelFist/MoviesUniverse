package com.example.moviesuniverse.presentation.screens.tabs.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.moviesuniverse.R
import com.example.moviesuniverse.domain.models.MovieItem

class MoviesAdapter(private val onItemClickListener: (id: String) -> Unit) :
    ListAdapter<MovieItem, MoviesViewHolder>(MoviesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)

        val viewHolder = MoviesViewHolder(view)
        val root = viewHolder.root

        root.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            if (position != NO_POSITION) {
                val id = currentList[position].id
                onItemClickListener.invoke(id)
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movieItem = getItem(position)
        holder.bind(movieItem)
    }

    companion object {
        private const val NO_POSITION = -1
    }
}