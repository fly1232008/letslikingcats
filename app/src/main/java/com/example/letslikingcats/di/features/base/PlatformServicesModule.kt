package com.example.letslikingcats.di.features.base

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import com.example.letslikingcats.MainActivity
import com.example.letslikingcats.utils.Permissions
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object PlatformServicesModule {

    @Singleton
    @Provides
    fun provideDownloadManager(application: Application) : DownloadManager {
        return application.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

}