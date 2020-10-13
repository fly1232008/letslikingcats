package com.example.letslikingcats.business.paginator

import io.reactivex.Observable

interface Paginator<T>: Iterator<Observable<List<T>>> {

}