package com.example.moviesuniverse.presentation.screens.tabs.sraff

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesuniverse.R
import com.example.moviesuniverse.databinding.MovieStaffItemBinding
import com.example.moviesuniverse.domain.models.MovieStaffItem

class MovieStaffAdapter(private val onItemClickListener: (id: String) -> Unit) :
    ListAdapter<MovieStaffItem, MovieStaffViewHolder>(MovieComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieStaffViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieStaffItemBinding.inflate(inflater, parent, false)

        val viewHolder = MovieStaffViewHolder(binding)

        binding.root.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            if (position != NO_POSITION) {
                val id = currentList[position].id
                onItemClickListener.invoke(id)
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: MovieStaffViewHolder, position: Int) {
        getItem(position)?.let { movieStaffItem ->
            holder.binding.tvMovieStaffItemTitle.text = movieStaffItem.nameRu

            val context = holder.binding.root.context

            Glide
                .with(context)
                .load(movieStaffItem.posterUrl)
                .centerCrop()
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error_placeholder)
                .into(holder.binding.ivMovieStaffItemPoster)
        }
    }

    companion object {
        private const val NO_POSITION = -1
    }
}

class MovieStaffViewHolder(val binding: MovieStaffItemBinding) :
    RecyclerView.ViewHolder(binding.root)

object MovieComparator : DiffUtil.ItemCallback<MovieStaffItem>() {
    override fun areItemsTheSame(oldItem: MovieStaffItem, newItem: MovieStaffItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieStaffItem, newItem: MovieStaffItem): Boolean {
        return oldItem == newItem
    }
}