package com.example.letslikingcats.utils

import io.reactivex.subjects.BehaviorSubject

fun <T> BehaviorSubject<List<T>>.notNullValue() : List<T> {
    value?.let { return it }
    return emptyList()
}

fun<T> BehaviorSubject<List<T>>.addAll(value: List<T>) =
    onNext(notNullValue().toMutableList().addAllAndGet(value))

fun <T> MutableList<T>.addAllAndGet(value: List<T>) = apply { addAll(value) }
