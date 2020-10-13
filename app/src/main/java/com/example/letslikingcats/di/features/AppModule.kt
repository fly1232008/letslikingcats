package com.example.letslikingcats.di.features

import com.example.letslikingcats.MainActivity
import com.example.letslikingcats.di.features.main.MainModule
import com.example.letslikingcats.di.features.base.PlatformServicesModule
import com.example.letslikingcats.di.features.base.RetrofitModule
import com.example.letslikingcats.di.features.base.RoomModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [RetrofitModule::class, RoomModule::class, PlatformServicesModule::class])
abstract class AppModule {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun contributeMainActivity(): MainActivity


}