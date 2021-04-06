package com.example.flickrtestapp.models

/*Wrapper for main data class model, for the json deserialisation works*/
data class Wrapper(val photos: PhotosObj)

data class PhotosObj(val photo: List<FlickrAppModel>)