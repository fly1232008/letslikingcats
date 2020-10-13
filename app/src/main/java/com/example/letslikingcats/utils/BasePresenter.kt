package com.example.letslikingcats.utils

import com.example.letslikingcats.ui.common.BindingPresenter
import moxy.MvpView
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.reflect.KProperty

open class BasePresenter<View: MvpView>: BindingPresenter<View>() {

    private val firstAttachDelegates = mutableListOf<FirstAttachDelegate<*>>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        firstAttachDelegates.forEach { it.inited.set(true) }
    }

    fun <T> BasePresenter<View>.afterFirstAttach(factory: () -> T): FirstAttachDelegate<T> {
        val delegate = FirstAttachDelegate(factory)
        firstAttachDelegates += delegate
        return delegate
    }

     class FirstAttachDelegate<T>(private val factory: () -> T) {

        private val value by lazy { factory() }

        val inited = AtomicBoolean()

        operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
            if (inited.get()) {
                return value
            } else {
                throw IllegalStateException("value available after presenter's onFirstViewAttach")
            }

        }

    }

}