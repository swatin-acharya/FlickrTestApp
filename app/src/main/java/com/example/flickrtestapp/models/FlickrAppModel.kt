package com.example.flickrtestapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/*Main data class Room annotations used for room DB integration*/
@Entity
data class FlickrAppModel(@PrimaryKey val id: Long,
                          val title: String,
                          val ownername: String,
                          var isFavourite: Boolean = false,
                          val url_m: String,
                          val width_m: Int,
                          val height_m: Int)