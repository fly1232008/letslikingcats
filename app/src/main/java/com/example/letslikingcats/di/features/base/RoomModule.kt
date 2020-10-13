package com.example.letslikingcats.di.features.base

import android.app.Application
import androidx.room.Room
import com.example.letslikingcats.data.database.AppDatabase
import com.example.letslikingcats.data.database.CatsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideCatsDao(appDatabase: AppDatabase) : CatsDao = appDatabase.catsDao()

    @Singleton
    @Provides
    fun provideAppDatabase(application: Application) =
        Room.databaseBuilder(application, AppDatabase::class.java, "cats-db").build()

}