package com.example.flickrtestapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flickrtestapp.databinding.FragmentPhotosBinding
import com.example.flickrtestapp.viewmodels.FlickrMainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

/*Same as Favourites Fragment*/
@AndroidEntryPoint
class PhotosFragment: Fragment() {
    
    private lateinit var binding: FragmentPhotosBinding
    private val flickrMainViewModel: FlickrMainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotosBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.photosFlickr.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.photosFlickr.setHasFixedSize(true)
        flickrMainViewModel.spinner.observe(viewLifecycleOwner) { show ->
            binding.spinner.visibility = if (show) View.VISIBLE else View.GONE
        }
        flickrMainViewModel.snackBar.observe(viewLifecycleOwner) { text ->
            text?.let {
                makeSnackBar(binding.root, text)
            }
        }
        val adapter = PhotosAdapter(flickrMainViewModel)
        binding.photosFlickr.adapter = adapter
        flickrMainViewModel.flickerData.observe(viewLifecycleOwner) {   photosListData ->
            photosListData?.let {
                adapter.submitList(it)
            }
        }
        return binding.root
    }

    private fun makeSnackBar(rootView: View, message: String) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
        flickrMainViewModel.clearSnackBar()
    }
}