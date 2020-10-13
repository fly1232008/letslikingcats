package com.example.letslikingcats.utils.compositeadapter

import android.view.ViewGroup
import moxy.MvpDelegate

abstract class DelegateAdapter<T> {

    lateinit var notifier: Notifier

    abstract fun onCreateViewHolder(
        parent: ViewGroup,
        delegate: MvpDelegate<*>
    ): CompositeAdapter.BaseViewHolder<T>

    abstract fun isForViewType(data: List<Any>, position: Int): Boolean

    interface Notifier {
        fun itemRemoved(position: Int)
    }
}