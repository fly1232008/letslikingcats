package com.example.letslikingcats.business.paginator

import com.example.letslikingcats.data.Offset
import io.reactivex.Observable

class SimplePaginator<T>(private val action: (Offset) -> Observable<List<T>>) : Paginator<T> {

    @Volatile
    private var loaded: Int = 0
    @Volatile
    private var isEnd = false

    override fun hasNext(): Boolean {
        return !isEnd
    }

    override fun next(): Observable<List<T>> {
        return action(loaded)
            .doOnNext {
                if (it.isEmpty()) {
                    isEnd = true
                }
            }
            .doOnNext { loaded += it.size }
    }

}



