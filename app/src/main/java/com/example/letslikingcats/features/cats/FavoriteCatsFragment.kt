package com.example.letslikingcats.features.cats

import android.os.Bundle
import com.example.letslikingcats.business.models.Cat
import com.example.letslikingcats.business.contracts.CatsUseCase
import com.example.letslikingcats.di.features.cats.CatsModule
import com.example.letslikingcats.di.HasInject
import com.example.letslikingcats.features.ListFragment
import com.example.letslikingcats.features.snippets.cat.CatDelegateAdapter
import com.example.letslikingcats.ui.UiPaginator
import com.example.letslikingcats.utils.compositeadapter.DelegateAdapter
import kotlinx.android.synthetic.main.fragment_cats.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Named

class FavoriteCatsFragment: ListFragment(), HasInject, CatsView {

    private lateinit var uiPaginator: UiPaginator

    @Inject
    @Named(CatsModule.FAVORITE)
    lateinit var useCase: CatsUseCase
    private val presenter by moxyPresenter { CatsPresenter(useCase) }
    @Inject
    lateinit var catDelegateAdapter: CatDelegateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        catDelegateAdapter.apply { permissions = permis }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        uiPaginator = UiPaginator(recycler) { presenter.onEndOfCatsReached() }
    }

    override fun delegates(): Array<DelegateAdapter<*>> = arrayOf(catDelegateAdapter)

    override fun showCats(cats: List<Cat>) = setData(cats)

    override fun addCats(cats: List<Cat>) = addData(cats)

    override fun endOfCats() {
        uiPaginator.isLastPage = true
    }

    override fun showLoadingNewPage() {
        uiPaginator.showLoadingNewPage()
    }

    override fun hideLoadingNewPage() {
        uiPaginator.hideLoadingNewPage()
    }

}