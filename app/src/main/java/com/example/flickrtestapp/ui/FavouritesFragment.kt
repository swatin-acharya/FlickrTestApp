package com.example.flickrtestapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrtestapp.databinding.FragmentFavouritesBinding
import com.example.flickrtestapp.models.FlickrAppModel
import com.example.flickrtestapp.viewmodels.FlickrMainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment: Fragment() {

    /*Databinding, Viewmodel, Recycler Adapter declaration*/
    private lateinit var binding: FragmentFavouritesBinding
    private val flickrMainViewModel: FlickrMainViewModel by activityViewModels()
    private lateinit var adapter: FavouritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.photosFlickr.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.photosFlickr.setHasFixedSize(true)

        flickrMainViewModel.snackBar.observe(viewLifecycleOwner) { text ->
            text?.let {
                makeSnackBar(binding.root, text)
            }
        }

        adapter = FavouritesAdapter(flickrMainViewModel)
        binding.photosFlickr.adapter = adapter

        /*Observe flickrData Livedata from viewmodel and apply to recycler*/
        flickrMainViewModel.flickerData.observe(viewLifecycleOwner) {   photosListData ->
            populateList(photosListData)
        }
        applyScrollListener()
        return binding.root
    }

    /*Filter the photos list to get only favourites and then show on recycler*/
    private fun populateList(originalList: List<FlickrAppModel>?) {
        originalList?.let {
            val onlyFavList = it.filter { it.isFavourite }
            adapter.submitList(onlyFavList)
            if (onlyFavList.isEmpty()) {binding.empty.visibility = View.VISIBLE }
            else {binding.empty.visibility = View.GONE}
        }
    }

    private fun applyScrollListener() {
        binding.photosFlickr.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                for (i in 0 until recyclerView.childCount) {
                    val viewHolder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i))
                    if (viewHolder is FavouritesAdapter.FavouritesItemHolder) {
                        viewHolder.animateImage()
                    }
                }
            }
        })
    }

    private fun makeSnackBar(rootView: View, message: String) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
        flickrMainViewModel.clearSnackBar()
    }
}