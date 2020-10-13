package com.example.letslikingcats.data.network

import com.example.letslikingcats.data.network.dto.CatDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CatsApi {

    @GET("images/search?order=Desc")
    fun cats(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
    ): Single<List<CatDto>>

}