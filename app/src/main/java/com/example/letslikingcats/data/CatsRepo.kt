package com.example.letslikingcats.data

import com.example.letslikingcats.data.network.CatsApi
import com.example.letslikingcats.data.network.dto.CatDto
import com.example.letslikingcats.utils.addAll
import com.example.letslikingcats.utils.notNullValue
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

typealias Offset = Int

@Singleton
class CatsRepo @Inject constructor(private val catsApi: CatsApi) {

    private val PAGE_SIZE = 100

    private val cache = BehaviorSubject.create<List<CatDto>>()

    fun load(offset: Offset): Observable<List<CatDto>> {
        return if (cache.notNullValue().size > offset) {
            cache
                .subscribeOn(Schedulers.io())
                .map { it.subList(offset, it.size) }
        } else {
            val page = offset / PAGE_SIZE
            catsApi.cats(PAGE_SIZE, page)
                .subscribeOn(Schedulers.io())
                .doOnSuccess { cache.addAll(it) }
                .toObservable()
        }
    }

}