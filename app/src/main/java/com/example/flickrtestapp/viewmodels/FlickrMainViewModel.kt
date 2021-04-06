package com.example.flickrtestapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickrtestapp.models.FlickrAppModel
import com.example.flickrtestapp.repository.FlickrRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/*Main viewmodel shared between the two fragments. Hilt used for injection*/
@HiltViewModel
class FlickrMainViewModel @Inject constructor(private val flickrRepo: FlickrRepo): ViewModel(){

    /*The spinner and snackbar are manipulated from view, these livedata allow the view to monitor what to do with the snackbar and spinner views*/
    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean> = _spinner
    private val _snackBar = MutableLiveData<String?>()
    val snackBar: LiveData<String?> = _snackBar

    /*Gets data from DB, sets to livedata, uses this data initially*/
    var flickerData: LiveData<List<FlickrAppModel>> = flickrRepo.getFlickrDataDB()

    fun clearSnackBar() {
        _snackBar.value = null
    }

    /*Called in the beginning, (after flickerdata set), api call made from here, if this fails, data still available from DB*/
    init {
        loadItems()
    }

    /*Function that takes suspend function as argument, runs it using viewmodelscope and catches error*/
    private fun launchSuspendLoading(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                block()
                _spinner.value = true
            } catch (error: Throwable) {
                _snackBar.value = error.message
            } finally {
                _spinner.value = false
            }
        }
    }

    /*Called from init*/
    private fun loadItems() {
        launchSuspendLoading {
            val flickrData = flickrRepo.getNetworkData().photos.photo
            flickrRepo.insertFlickerDataToDB(flickrData)
        }
    }

    /*Called from XML databinding to toggle the favourites button, here correspondingly, I update database with new value*/
    fun onFavouriteToggled(flickrAppModel: FlickrAppModel) {
        launchSuspendLoading {
            flickrRepo.updateFavouriteValue(!flickrAppModel.isFavourite, flickrAppModel.id)
        }
    }
}