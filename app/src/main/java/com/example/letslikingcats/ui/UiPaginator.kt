package com.example.letslikingcats.ui

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letslikingcats.utils.paginationdecorator.PaginationLoadingDecoration


class UiPaginator(private val recyclerView: RecyclerView, val next: (Int) -> Unit): RecyclerView.OnScrollListener() {

    private val layoutManager: GridLayoutManager
    private var isLoading = false
    var isLastPage = false
    val paginationLoadingDecoration = PaginationLoadingDecoration()

    init {
        recyclerView.addOnScrollListener(this)
        if (recyclerView.layoutManager is GridLayoutManager) {
            layoutManager = recyclerView.layoutManager as GridLayoutManager
        } else {
            throw IllegalStateException("supported only LinearLayoutManager")
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

        if (!isLoading && !isLastPage && lastVisibleItemPosition == (totalItemCount - 1)) {
            isLoading = true
            next(totalItemCount)
        }
    }

    fun showLoadingNewPage() {
        isLoading = true
        recyclerView.addItemDecoration(paginationLoadingDecoration)
    }

    fun hideLoadingNewPage() {
        isLoading = false
        recyclerView.removeItemDecoration(paginationLoadingDecoration)
    }
}
