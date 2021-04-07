package com.example.flickrtestapp.ui

import android.graphics.Matrix
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrtestapp.databinding.FavouritesListItemBinding
import com.example.flickrtestapp.models.FlickrAppModel
import com.example.flickrtestapp.viewmodels.FlickrMainViewModel

/*Typical recycler adapter with DiffUtils and data binding, ViewModel and Model Object injected into single row layout,
the favourite button toggle is delivered straight to viewmodel via data binding*/
class FavouritesAdapter(private val flickrMainViewModel: FlickrMainViewModel) :
    ListAdapter<FlickrAppModel, FavouritesAdapter.FavouritesItemHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesItemHolder {
        return FavouritesItemHolder(
            flickrMainViewModel,
            FavouritesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavouritesItemHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class FavouritesItemHolder(
        private val flickrMainViewModel: FlickrMainViewModel,
        private val binding: FavouritesListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FlickrAppModel) {
            binding.apply {
                model = item
                viewModel = flickrMainViewModel
                executePendingBindings()
            }
        }

        fun animateImage() {
            if(binding.ivArticleImage.drawable != null) {
                val translate = -itemView.y * (binding.ivArticleImage.measuredHeight.toFloat() /
                        (itemView.parent as RecyclerView).measuredHeight.toFloat())
                val imageMatrix = Matrix()
                imageMatrix.postTranslate(0F, translate)
                binding.ivArticleImage.imageMatrix = imageMatrix
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<FlickrAppModel>() {
        override fun areItemsTheSame(
            oldItem: FlickrAppModel,
            newItem: FlickrAppModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FlickrAppModel,
            newItem: FlickrAppModel
        ): Boolean {
            return oldItem == newItem
        }

    }
}