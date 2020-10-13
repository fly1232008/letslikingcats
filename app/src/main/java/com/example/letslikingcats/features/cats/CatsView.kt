package com.example.letslikingcats.features.cats

import com.example.letslikingcats.business.models.Cat
import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution

@OneExecution
interface CatsView: MvpView {

    fun showCats(cats: List<Cat>)
    fun addCats(cats: List<Cat>)
    fun endOfCats()
    fun showLoadingNewPage()
    fun hideLoadingNewPage()

}