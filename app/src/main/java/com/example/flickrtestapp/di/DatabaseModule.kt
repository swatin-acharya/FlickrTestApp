package com.example.flickrtestapp.di

import android.content.Context
import androidx.room.Room
import com.example.flickrtestapp.database.AppDatabase
import com.example.flickrtestapp.database.FlickrDao
import com.example.flickrtestapp.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*Hilt Dagger Provider objects for dependency injection of Database instance*/
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    internal fun provideAppDatabaseIntance(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    internal fun provideFlickrDao(appDatabase: AppDatabase): FlickrDao {
        return appDatabase.flickrAppDao
    }
}