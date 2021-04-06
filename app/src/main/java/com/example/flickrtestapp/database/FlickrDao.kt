package com.example.flickrtestapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flickrtestapp.models.FlickrAppModel

/*Dao object for DB operations*/
@Dao
interface FlickrDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<FlickrAppModel>)

    @Query("select * from FlickrAppModel")
    fun getItemsDB(): LiveData<List<FlickrAppModel>>

    @Query("select * from FlickrAppModel where id= :id")
    fun getItemDBById(id: Int): LiveData<FlickrAppModel>

    @Query("update FlickrAppModel set isFavourite= :isFavourite where id= :id")
    suspend fun updateSingleItem(isFavourite: Boolean, id: Long)
}