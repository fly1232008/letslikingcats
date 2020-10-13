package com.example.letslikingcats.di.features.base

import com.example.letslikingcats.data.network.CatsApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, adapterFactory: CallAdapter.Factory): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(adapterFactory)
            .build()

    @Singleton
    @Provides
    fun provideCatsApi(retrofit: Retrofit): CatsApi = retrofit.create(CatsApi::class.java)

    @Singleton
    @Provides
    fun provideGson() = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideRetrofitAdapter(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

}