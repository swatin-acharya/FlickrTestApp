package com.example.flickrtestapp.repository

import androidx.lifecycle.LiveData
import com.example.flickrtestapp.database.FlickrDao
import com.example.flickrtestapp.models.FlickrAppModel
import com.example.flickrtestapp.models.Wrapper
import com.example.flickrtestapp.network.Api
import com.example.flickrtestapp.utils.FLICKR_API_KEY
import com.example.flickrtestapp.utils.FLICKR_USER_ID_1
import javax.inject.Inject

/*Added layer to mvvm for single source of data as seen by viewmodel, here are functions to add/remove data from DB and Network*/
class FlickrRepo @Inject constructor(
    private val flickrApi: Api,
    private val flickrDao: FlickrDao){

    fun getFlickrDataDB(): LiveData<List<FlickrAppModel>> {
        return flickrDao.getItemsDB()
    }

    suspend fun getNetworkData(): Wrapper {
        return flickrApi.getFlickrData(FLICKR_API_KEY, FLICKR_USER_ID_1)
    }

    suspend fun insertFlickerDataToDB(flickrAppModelList: List<FlickrAppModel>) {
        flickrDao.insertItems(flickrAppModelList)
    }

    suspend fun updateFavouriteValue(isFavourite: Boolean, id: Long) {
        flickrDao.updateSingleItem(isFavourite, id)
    }
}