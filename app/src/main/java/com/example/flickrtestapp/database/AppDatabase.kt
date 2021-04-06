package com.example.flickrtestapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flickrtestapp.models.FlickrAppModel

/*Room Database initialisation*/
@Database(entities = [FlickrAppModel::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract val flickrAppDao: FlickrDao
}