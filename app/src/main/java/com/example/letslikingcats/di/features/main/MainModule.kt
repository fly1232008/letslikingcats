package com.example.letslikingcats.di.features.main

import com.example.letslikingcats.di.features.cats.CatsModule
import com.example.letslikingcats.features.cats.AllCatsFragment
import com.example.letslikingcats.features.cats.FavoriteCatsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {


    @ContributesAndroidInjector(modules = [CatsModule::class])
    abstract fun contributeAllCatsFragment(): AllCatsFragment

    @ContributesAndroidInjector(modules = [CatsModule::class])
    abstract fun contributeFavoriteCatsFragment(): FavoriteCatsFragment

}