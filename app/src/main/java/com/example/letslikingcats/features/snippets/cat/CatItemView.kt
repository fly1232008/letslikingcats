package com.example.letslikingcats.features.snippets.cat

import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution

@OneExecution
interface CatItemView: MvpView {
    fun markFavorite()
    fun markUnFavorite()
    fun showSavingImage()
    fun showImageSaved()
}