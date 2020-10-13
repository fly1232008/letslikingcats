package com.example.letslikingcats.utils.compositeadapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import moxy.MvpDelegate

class CompositeAdapter(val delegate: MvpDelegate<*>, vararg adapters: DelegateAdapter<*>): RecyclerView.Adapter<CompositeAdapter.BaseViewHolder<*>>() {

    private val adapters = adapters.toList()
    private val data = mutableListOf<Any>()

    init {
        adapters.forEach { it.notifier = object: DelegateAdapter.Notifier {

            override fun itemRemoved(position: Int) {
                data.removeAt(position)
                notifyItemRemoved(position)
            }

        } }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return adapters[viewType].onCreateViewHolder(parent, delegate)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        return holder.bindRaw(data[position])
    }

    override fun getItemViewType(position: Int): Int {
        return adapters.indexOfFirst { it.isForViewType(data, position) }
    }

    override fun getItemCount(): Int = data.size

    override fun onViewDetachedFromWindow(holder: BaseViewHolder<*>) {
        holder.onDetach()
    }

    fun addData(newData: List<Any>) {
        val beforeSize = data.size
        data.addAll(newData)
        notifyItemRangeChanged(beforeSize, newData.size)
    }

    fun setData(newData: List<Any>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    abstract class BaseViewHolder<T>(
        itemView: View,
        private val parentDelegate: MvpDelegate<*>
    ) : RecyclerView.ViewHolder(itemView) {

        private lateinit var delegate: MvpDelegate<*>
        private val presentersMap = hashMapOf<String, MvpDelegate<*>>()
        protected lateinit var tag: String

        open fun bind(t: T) {
        }

        fun bindRaw(any: Any) {
            tag = any.toString()
            delegate = presentersMap.getOrElse(tag) {
                MvpDelegate(this).apply {
                    setParentDelegate(parentDelegate, tag)
                    onCreate()
                }
            }
            bind(any as T)
            onAttach()
        }

        fun onAttach() {
            delegate.onAttach()
        }

        fun onDetach() {
            delegate.onSaveInstanceState()
            delegate.onDetach()
        }

    }
}