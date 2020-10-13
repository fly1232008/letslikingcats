package com.example.letslikingcats.business.paginator

import io.reactivex.Observable

class SinglePagePaginator<T>(private val action: () -> Observable<List<T>>): Paginator<T> {

    @Volatile
    private var isEnd = false

    override fun hasNext(): Boolean = !isEnd

    override fun next(): Observable<List<T>> =
        action()
            .doOnNext { isEnd = true }
}