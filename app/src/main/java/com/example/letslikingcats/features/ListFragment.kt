package com.example.letslikingcats.features

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.letslikingcats.R
import com.example.letslikingcats.ui.InjectableFragment
import com.example.letslikingcats.utils.Permissible
import com.example.letslikingcats.utils.Permissions
import com.example.letslikingcats.utils.SpacesItemDecoration
import com.example.letslikingcats.utils.compositeadapter.CompositeAdapter
import com.example.letslikingcats.utils.compositeadapter.DelegateAdapter
import kotlinx.android.synthetic.main.fragment_cats.*

abstract class ListFragment: InjectableFragment(R.layout.fragment_cats) {

    protected lateinit var permis: Permissions

    private val adapter by lazy {
        CompositeAdapter(mvpDelegate, *delegates())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Permissible) {
            permis = context.permissions()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.layoutManager = GridLayoutManager(context, 3)
        recycler.adapter = adapter
        recycler.addItemDecoration(SpacesItemDecoration(resources.getDimension(R.dimen.space_divider).toInt()))
    }

    fun addData(data: List<Any>) {
        adapter.addData(data)
    }

    fun setData(data: List<Any>) {
        adapter.setData(data)
    }

    abstract fun delegates() : Array<DelegateAdapter<*>>

}