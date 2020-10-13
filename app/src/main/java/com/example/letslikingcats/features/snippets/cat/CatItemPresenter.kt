package com.example.letslikingcats.features.snippets.cat


import android.Manifest
import android.util.Log
import com.example.letslikingcats.business.models.Cat
import com.example.letslikingcats.business.cats.FavoriteToggleUseCase
import com.example.letslikingcats.features.downloadfiles.FileManager
import com.example.letslikingcats.utils.BasePresenter
import com.example.letslikingcats.utils.Permissions
import javax.inject.Inject


const val TAG = "CatItemPresenter"
class CatItemPresenter @Inject constructor(
    private val useCase: FavoriteToggleUseCase,
    private var fileManager: FileManager,
    private val permissions: Permissions
): BasePresenter<CatItemView>() {

    private var state: State = State.UNFAVORITE
    lateinit var item: Cat

    override fun attachView(view: CatItemView?) {
        super.attachView(view)
        updateState()
    }

    fun item(item: Cat) {
        this.item = item
        state = when(item.favorite) {
            true -> State.FAVORITE
            false -> State.UNFAVORITE
        }
    }

    fun onDoubleTap() {
        bind(onUI(
            when (state) {
                State.FAVORITE -> {
                    state = State.UNFAVORITE
                    updateState()
                    useCase.unFavorite(item)
                        .doOnError { state = State.FAVORITE }
                }
                State.UNFAVORITE -> {
                    state = State.FAVORITE
                    updateState()
                    useCase.favorite(item)
                        .doOnError { state = State.UNFAVORITE }
                }
            }
        ).subscribe(
            { },
            {
                Log.w(TAG, it)
                updateState()
            }
        ), LifeLevel.PER_PRESENTER)
    }

    private fun updateState() {
        when(state) {
            State.FAVORITE -> viewState.markFavorite()
            State.UNFAVORITE -> viewState.markUnFavorite()
        }
    }

    fun onLongPress() {
        bind(onUI(permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .doOnComplete { viewState.showSavingImage() }
            .andThen(fileManager.saveByUrl(item.url))
            .subscribe(
                { viewState.showImageSaved() },
                { Log.w(TAG, it) }), LifeLevel.PER_PRESENTER)
    }

    enum class State {
        UNFAVORITE, FAVORITE
    }
}