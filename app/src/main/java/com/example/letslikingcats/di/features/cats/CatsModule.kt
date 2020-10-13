package com.example.letslikingcats.di.features.cats

import com.example.letslikingcats.business.cats.AllCatsUseCase
import com.example.letslikingcats.business.contracts.CatsUseCase
import com.example.letslikingcats.business.cats.FavoriteCatsUseCase
import com.example.letslikingcats.data.CatsRepo
import com.example.letslikingcats.data.database.CatsDao
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object CatsModule {

    //companion object {
        const val ALL = "all"
        const val FAVORITE = "favorite"
    //}

    //@Binds
    //@Named(ALL)
    //abstract fun bindAllCatsUseCase(impl: AllCatsUseCase) : CatsUseCase

    @Provides
    @Named(ALL)
    fun provideAllCatsUseCase(catsRepo: CatsRepo, catsDao: CatsDao): CatsUseCase {
        return AllCatsUseCase(catsRepo, catsDao)
    }

    @Provides
    @Named(FAVORITE)
    fun provideFavoriteCatsUseCase(catsDao: CatsDao): CatsUseCase {
        return FavoriteCatsUseCase(catsDao)
    }

    //@Binds
    //@Named(FAVORITE)
    //abstract fun bindFavoriteCatsUseCase(impl: FavoriteCatsUseCase) : CatsUseCase

}