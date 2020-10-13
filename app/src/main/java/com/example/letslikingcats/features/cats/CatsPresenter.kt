package com.example.letslikingcats.features.cats

import android.util.Log
import com.example.letslikingcats.business.models.Cat
import com.example.letslikingcats.business.contracts.CatsUseCase
import com.example.letslikingcats.business.paginator.Paginator

import com.example.letslikingcats.utils.BasePresenter

const val TAG = "CatsPresenter"

class CatsPresenter(private val catsUseCase: CatsUseCase):
    BasePresenter<CatsView>() {

    private lateinit var catPaginator: Paginator<Cat>

    override fun attachView(view: CatsView?) {
        super.attachView(view)
        catPaginator = catsUseCase.cats()
        if (!catPaginator.hasNext()) {
            viewState.endOfCats()
        } else {
            bind(onUI(catPaginator.next()).subscribe(
                { viewState.showCats(it) },
                { Log.w("", it) })
            )
        }
    }

    fun onEndOfCatsReached() {
        if (!catPaginator.hasNext()) {
            viewState.endOfCats()
            return
        }
        bind(onUI(catPaginator.next())
            .doOnSubscribe { viewState.showLoadingNewPage() }
            .doOnTerminate { viewState.hideLoadingNewPage() }
            .doOnNext { viewState.hideLoadingNewPage()  }
            .subscribe(
                { viewState.addCats(it) },
                { Log.w(TAG, it) }
            ))
    }
}

